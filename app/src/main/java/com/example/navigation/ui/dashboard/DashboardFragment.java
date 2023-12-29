package com.example.navigation.ui.dashboard;


import com.example.navigation.R;
import com.example.navigation.ImageData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;


import com.example.navigation.databinding.FragmentDashboardBinding;
import com.example.navigation.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;
public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private List<ImageData> imageDataList; // ImageData 객체 리스트

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // JSON 파일에서 ImageData 객체 리스트 로드
        imageDataList = loadImageDataFromJson(getContext());

        // RecyclerView 설정
        RecyclerView recyclerView = binding.galleryRecyclerview;
        GalleryAdapter adapter = new GalleryAdapter(getContext(), imageDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }

    private List<ImageData> loadImageDataFromJson(Context context) {
        List<ImageData> imageDataList = new ArrayList<>();
        String json = ""; // 여기서 변수 선언

        try {
            InputStream inputStream = context.getAssets().open("image_data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, "UTF-8"); // 값을 할당
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String imageResName = jsonObject.getString("imageResId");
                int imageResId = context.getResources().getIdentifier(imageResName, "drawable", context.getPackageName());
                String name = jsonObject.getString("name");
                String features = jsonObject.getString("features");
                String price = jsonObject.getString("price");

                imageDataList.add(new ImageData(imageResId, name, features, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DashboardFragment", "Error loading JSON: " + json, e); // 로그 출력
        }

        return imageDataList;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

