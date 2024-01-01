package com.example.navigation.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;
import com.example.navigation.RestaurantAdapter;
import com.example.navigation.RestaurantItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<RestaurantItem> restaurantList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("DetailsFragment", "onCreateView called");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // JSON 데이터 로드
        restaurantList = com.example.navigation.util.JsonParser.parseRestaurantsJson(getContext(), "restaurants.json");

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RestaurantAdapter(getContext(), (ArrayList<RestaurantItem>) restaurantList);
        recyclerView.setAdapter(adapter);

        // Item 클릭 리스너
        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("HomeFragment", "아이템 클릭됨, 위치: " + position);
                RestaurantItem selectedRestaurant = restaurantList.get(position);
                showDetailsFragment(selectedRestaurant);
            }
        });


        return view;
    }

    private void showDetailsFragment(RestaurantItem restaurant) {
        Log.d("HomeFragment", "showDetailsFragment 시작");

        DetailsRestaurantFragment detailsFragment = new DetailsRestaurantFragment();
        Bundle args = new Bundle();
        args.putSerializable("restaurant", restaurant);
        detailsFragment.setArguments(args);

        Log.d("HomeFragment", "프래그먼트 설정 완료");

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_container, detailsFragment)
                .addToBackStack(null)
                .commit();

        Log.d("HomeFragment", "프래그먼트 트랜잭션 커밋됨");
    }
}