package com.example.navigation.ui.home;

import androidx.lifecycle.ViewModel;
import com.example.navigation.RestaurantItem;
import com.example.navigation.util.JsonParser;
import java.util.ArrayList;
import android.content.Context;

public class HomeViewModel extends ViewModel {
    private ArrayList<RestaurantItem> restaurantList;
    private Context context;

    public HomeViewModel(Context context) {
        this.context = context;
        this.restaurantList = (ArrayList<RestaurantItem>) JsonParser.parseRestaurantsJson(context, "restaurants.json");
    }

    public ArrayList<RestaurantItem> getRestaurantList() {
        return restaurantList;
    }
}
