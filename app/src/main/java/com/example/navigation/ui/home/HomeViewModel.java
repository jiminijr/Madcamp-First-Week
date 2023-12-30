package com.example.navigation.ui.home;

import androidx.lifecycle.ViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;

import java.nio.charset.StandardCharsets;

public class HomeViewModel extends ViewModel {
    private ArrayList<com.example.navigation.RestaurantItem> restaurantList = new ArrayList<>();
    private Context context;

    public HomeViewModel(Context context) {
        this.context = context;
        this.loadRestaurants();
    }

    public ArrayList<com.example.navigation.RestaurantItem> getRestaurantList() {
        return restaurantList;
    }

    private void loadRestaurants() {
        try {
            InputStream is = this.context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONObject(json).getJSONArray("restaurant");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String outImgName = jsonObject.getString("out_img");
                int outImgResId = context.getResources().getIdentifier(outImgName, "drawable", context.getPackageName());
                String name = jsonObject.getString("name");
                String number = jsonObject.getString("number");
                String address = jsonObject.getString("address");

                this.restaurantList.add(new com.example.navigation.RestaurantItem(outImgResId, name, number, address));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
        }
    }
}