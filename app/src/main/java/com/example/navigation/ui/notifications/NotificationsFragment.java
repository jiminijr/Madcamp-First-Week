package com.example.navigation.ui.notifications;

import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
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
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements OnMapReadyCallback{

    // 식당 마커 작성을 위해 위도 경도를 저장하는 Pos를 만듬
    static class Pos {
        double lat;
        double lon;
        String text;
        public Pos(double x, double y, String string){
            lat = x;
            lon = y;
            text = string;
        }
        public double getLat(){
            return lat;
        }
        public double getLong(){
            return lon;
        }
        public String getText() { return text; }

    }

    private  static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private FloatingActionButton cur_loc_button;

    private View rootView;
    public NotificationsFragment(){}


    // Json으로 부터 위도, 경도 읽어오기
    public ArrayList<Pos> getMarkerPos(){
        ArrayList<Pos> ret = new ArrayList<>();
        try {
            InputStream is = getContext().getAssets().open("res.json"); // Merge 후에 파일명 restaurant.json으로 변경
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            ArrayList<Pos> pos_list = new ArrayList<>();
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONObject(json).getJSONArray("restaurant");

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double lat = Double.parseDouble(jsonObject.getString("lat"));
                double lon = Double.parseDouble(jsonObject.getString("lon"));
                String text = jsonObject.getString("name");
                pos_list.add(new Pos(lat, lon, text));
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
        // 지도 확대 축소 제한
        naverMap.setMaxZoom(18.0);
        naverMap.setMinZoom(12.0);
        // 현재 위치 tracking
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Face);

        // json 읽어서 마커정보 표시하기 + InfoWindow 창 만들기

        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(requireContext()) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "정보창";
            }
        });

        // 맵 클릭시 infowindow 닫기
        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                infoWindow.close();
            }
        });

        ArrayList<Pos> marker_pos_list = getMarkerPos();
        for(Pos pos : marker_pos_list){
            Marker marker = new Marker();
            LatLng position = new LatLng(pos.getLat(), pos.getLong());
            marker.setPosition(position);
            marker.setWidth(50);
            marker.setHeight(50);
            marker.setIcon(MarkerIcons.BLACK);
            marker.setIconTintColor(Color.RED);
            marker.setCaptionText(pos.getText());
            marker.setMap(naverMap);
            marker.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    if(marker.getInfoWindow() == null){
                        infoWindow.open(marker);
                    }
                    else{
                        infoWindow.close();
                    }
                    return true;
                }
            });
        }

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
            }
        });
    }

}