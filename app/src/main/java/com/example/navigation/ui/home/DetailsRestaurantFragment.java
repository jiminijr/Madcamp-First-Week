package com.example.navigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.navigation.Menu;
import com.example.navigation.R;
import com.example.navigation.RestaurantItem;

public class DetailsRestaurantFragment extends Fragment {

    private RestaurantItem restaurantItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_restaurant, container, false);
        if (getArguments() != null) {
            restaurantItem = (RestaurantItem) getArguments().getSerializable("restaurant");
            if (restaurantItem != null) {
                updateDetailsView(view);
            } else {
            }
        } else {
        }

        return view;
    }

    private void updateDetailsView(View view) {

        if (!restaurantItem.getMenuList().isEmpty()) {
            updateMenuLayout(view, restaurantItem.getMenuList().get(0), R.id.frame1, R.id.image1, R.id.text1, R.id.text1_1);

            // 두 번째 메뉴 아이템 (있는 경우)
            if (restaurantItem.getMenuList().size() > 1) {
                updateMenuLayout(view, restaurantItem.getMenuList().get(1), R.id.frame2, R.id.image2, R.id.text2, R.id.text2_1);
            }

            // 세 번째 메뉴 아이템 (있는 경우)
            if (restaurantItem.getMenuList().size() > 2) {
                updateMenuLayout(view, restaurantItem.getMenuList().get(2), R.id.frame3, R.id.image3, R.id.text3, R.id.text3_1);
            }
        }
    }

    private void updateMenuLayout(View view, Menu menu, int frameId, int imageId, int textId, int textPriceId) {
        FrameLayout frameLayout = view.findViewById(frameId);
        ImageView imageView = frameLayout.findViewById(imageId);
        TextView textView = frameLayout.findViewById(textId);
        TextView textPriceView = frameLayout.findViewById(textPriceId);

        imageView.setImageResource(menu.getMenuImgResId()); // 이미지 리소스 ID로 설정
        textView.setText(menu.getMenuName()); // 메뉴 이름
        textPriceView.setText(menu.getMenuPrice()); // 메뉴 가격
    }
}
