package com.example.navigation.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

import com.example.navigation.PhoneListItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HomeViewModel extends ViewModel {
    private ArrayList<PhoneListItem> phoneList = new ArrayList<>();
    private Context context;

    public HomeViewModel(Context context) {
        this.context = context;
        this.loadPhones();
    }

    public ArrayList<PhoneListItem> getPhoneList() {
    /*
        ArrayList<PhoneListItem> test =  new ArrayList<>();
        for(int i=0; i<20; i++) {
            test.add(new PhoneListItem("K", "010-2656-4192"));
        }
        return test;
    */
        return phoneList;
    }

    private void loadPhones() {
        try {
            InputStream is = this.context.getAssets().open("phone_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONObject(json).getJSONArray("phones");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String number = jsonObject.getString("number");
                this.phoneList.add(new PhoneListItem(name, number));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 에러 처리
        }
    }
}
