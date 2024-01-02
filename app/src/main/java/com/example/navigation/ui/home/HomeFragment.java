package com.example.navigation.ui.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;
import com.example.navigation.RestaurantAdapter;
import com.example.navigation.RestaurantItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


    // Floating Button 처리
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.search_tab);
        LinearLayout searchTabContainer = (LinearLayout) view.findViewById(R.id.search_tab_container);
        searchTabContainer.setVisibility(View.GONE); // 초기 상태에서 숨김
        SearchFragment searchFragment = new SearchFragment(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            private boolean open = false;

            @Override
            public void onClick(View v) {
                open = !open;

                if(open) {
                    // SearchFragment를 추가하고 LinearLayout을 보이게 함
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.search_tab_container, searchFragment)
                            .addToBackStack(null)
                            .commit();
                    searchTabContainer.setVisibility(View.VISIBLE);
                    floatingActionButton.setImageResource(R.drawable.ic_cross_black_24dp); // 새 아이콘으로 변경
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.dark_blue_700))); // 새 색상으로 변경
                }
                else {
                    // Fragment를 제거하고 LinearLayout을 숨김
                    getActivity().getSupportFragmentManager().popBackStack();
                    searchTabContainer.setVisibility(View.GONE);
                    floatingActionButton.setImageResource(R.drawable.ic_search_black_24dp);
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.light_blue_200)));
                }
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