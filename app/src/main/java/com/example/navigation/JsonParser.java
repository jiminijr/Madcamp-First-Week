package com.example.navigation.util;

import android.content.Context;

import com.example.navigation.Menu;
import com.example.navigation.RestaurantItem;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<RestaurantItem> parseRestaurantsJson(Context context, String jsonFileName) {
        List<RestaurantItem> restaurantList = new ArrayList<>();
        try {
            InputStream inputStream = context.getAssets().open(jsonFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONObject(json).getJSONArray("restaurant");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String outImgName = jsonObject.getString("out_img");
                int outImgResId = context.getResources().getIdentifier(outImgName, "drawable", context.getPackageName());
                String foodImgName = jsonObject.getString("food_img");
                int foodImgResId = context.getResources().getIdentifier(foodImgName, "drawable", context.getPackageName());
                String name = jsonObject.getString("name");
                String address = jsonObject.getString("address");
                String number = jsonObject.getString("number");
                String info = jsonObject.getString("info");
                String tag = jsonObject.getString("tag");

                JSONArray menuArray = jsonObject.getJSONArray("menu");
                List<Menu> menuList = new ArrayList<>();
                for (int j = 0; j < menuArray.length(); j++) {
                    JSONObject menuObject = menuArray.getJSONObject(j);
                    String menuName = menuObject.getString("menu_name");
                    String menuImgName = menuObject.getString("menu_img");
                    int menuImgResId = context.getResources().getIdentifier(menuImgName, "drawable", context.getPackageName());
                    String menuPrice = menuObject.getString("menu_price");
                    menuList.add(new Menu(menuName, menuImgResId, menuPrice));
                }

                restaurantList.add(new RestaurantItem(outImgResId, foodImgResId, name, address, number, info, tag, menuList));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurantList;
    }
}
