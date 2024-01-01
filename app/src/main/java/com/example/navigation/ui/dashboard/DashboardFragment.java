package com.example.navigation.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.navigation.RestaurantItem;
import com.example.navigation.databinding.FragmentDashboardBinding;
import com.example.navigation.GalleryAdapter;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // JSON 파일에서 RestaurantItem 리스트 로드
        List<RestaurantItem> restaurantList = com.example.navigation.util.JsonParser.parseRestaurantsJson(getContext(), "restaurants.json");

        // RecyclerView 설정
        RecyclerView recyclerView = binding.galleryRecyclerview;
        GalleryAdapter adapter = new GalleryAdapter(getContext(), restaurantList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
