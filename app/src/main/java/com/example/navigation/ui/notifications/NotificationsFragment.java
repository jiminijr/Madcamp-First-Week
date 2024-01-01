package com.example.navigation.ui.notifications;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import com.example.navigation.MarkerAdapter;
import com.example.navigation.MarkerItem;
import com.example.navigation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;
import com.naver.maps.map.widget.ScaleBarView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class NotificationsFragment extends Fragment implements OnMapReadyCallback{

    private  static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private FloatingActionButton cur_loc_button;
    private View rootView;
    public NotificationsFragment(){}


    // Json으로 부터 위도, 경도 읽어오기 - 이 부분만 merge 후에 수정하면 될듯
    public ArrayList<MarkerItem> getMarkerInfo(){
        ArrayList<MarkerItem> ret = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("res.json"); // Merge 후에 파일명 restaurant.json으로 변경
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            ArrayList<MarkerItem> pos_list = new ArrayList<>();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONObject(json).getJSONArray("restaurant");

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double lat = Double.parseDouble(jsonObject.getString("lat"));
                double lon = Double.parseDouble(jsonObject.getString("lon"));
                String name = jsonObject.getString("name");
                String phone = jsonObject.getString("number");
                String address = jsonObject.getString("address");
                String info = jsonObject.getString("info");
                String foodimg = jsonObject.getString("food_img");
                int foodimg_id = getContext().getResources().getIdentifier(foodimg, "drawable", getContext().getPackageName());
                String link = jsonObject.getString("link");

                pos_list.add(new MarkerItem(lat, lon, name, phone, address, info, foodimg_id, link));
            }
            return pos_list;

        } catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        FragmentContainerView fragmentContainerView = rootView.findViewById(R.id.map);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if(mapFragment == null){
            mapFragment = MapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        cur_loc_button = rootView.findViewById(R.id.curloc);
        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap){

        this.naverMap = naverMap;
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setCompassEnabled(false);

        ScaleBarView scaleBarView = rootView.findViewById(R.id.scalebar);
        scaleBarView.setMap(naverMap);

        // 지도 확대 축소 제한
        naverMap.setMaxZoom(18.0);
        naverMap.setMinZoom(12.0);

        // 현재 위치 tracking
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
        // Marker & Infowindow
        ArrayList<MarkerItem> marker_pos_list = getMarkerInfo();
        ArrayList<InfoWindow> infoWindows = new ArrayList<>();
        ArrayList<Marker> markers = new ArrayList<>();

        for(MarkerItem item : marker_pos_list){
            // Marker Position
            Marker marker = new Marker();
            LatLng position = new LatLng(item.getLat(), item.getLon());
            marker.setPosition(position);
            marker.setHideCollidedMarkers(true);
            // Marker Design
            marker.setIcon(MarkerIcons.BLACK);
            marker.setIconTintColor(Color.RED);
            marker.setWidth(Marker.SIZE_AUTO);
            marker.setHeight(Marker.SIZE_AUTO);

            // Caption Setting
            marker.setCaptionText(item.getName());
            marker.setCaptionMinZoom(14.0);
            marker.setCaptionMaxZoom(18.0);

            // Marker Adding
            marker.setMap(naverMap);
            markers.add(marker);
            // InfoWindow 설정
            InfoWindow infoWindow = new InfoWindow();
            ViewGroup rootView = (ViewGroup) getView().findViewById(R.id.map);

            infoWindow.setOnClickListener(new Overlay.OnClickListener(){
                @Override
                public boolean onClick(@NonNull Overlay overlay){
                    if(!item.getLink().equals("None")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://" + item.getLink()));
                        startActivity(intent);
                    }
                    return true;
                }
            });

            infoWindow.setAdapter(new MarkerAdapter(requireContext(), rootView, item));
            infoWindows.add(infoWindow);

        }

        // Marker Click Event 처리
        int size = markers.size();
        for(int i=0; i<size; ++i){
            Marker marker = markers.get(i);
            InfoWindow infoWindow = infoWindows.get(i);
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    if(marker.getInfoWindow() == null){
                        for(InfoWindow infoWindow : infoWindows){
                            infoWindow.close();
                        }
                       infoWindow.open(marker);
                    }
                    else{
                       infoWindow.close();
                    }
                    return true;
                }
            });
        }

        // Map Click Event 처리
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                for(InfoWindow infoWindow : infoWindows){
                    infoWindow.close();
                }
            }
        });
         

        // Current Location Button Click Event 처리
        cur_loc_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Location cur = locationSource.getLastLocation();
                if(cur != null) {
                    CameraPosition currentcameraPosition = naverMap.getCameraPosition();
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(cur), currentcameraPosition.zoom);
                    naverMap.setCameraPosition(cameraPosition);
                    naverMap.setLocationTrackingMode(LocationTrackingMode.Face);
                }
                for(InfoWindow infoWindow : infoWindows){
                    infoWindow.close();
                }
            }
        });
    }
}