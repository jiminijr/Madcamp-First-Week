package com.example.navigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;
import com.example.navigation.RestaurantAdapter;

public class HomeFragment extends Fragment {
    private HomeViewModel viewModel;
    private RestaurantAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // RecyclerView 설정
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adapter 적용
        viewModel = new HomeViewModel(getContext());
        adapter = new RestaurantAdapter(getContext(), viewModel.getRestaurantList());
        recyclerView.setAdapter(adapter);

        // Item 클릭 리스너 설정 (예시)
        // recyclerView.setOnItemClickListener(...);

        return view;
    }
}
