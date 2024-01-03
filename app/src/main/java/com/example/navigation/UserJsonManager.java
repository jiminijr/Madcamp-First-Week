package com.example.navigation;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UserJsonManager {
    private String filename;
    private Context context;
    private JSONObject json_obj;

    private List<RestaurantItem> res_list;
    public UserJsonManager(String filename, Context context){
        this.filename = filename;
        this.context = context;
        this.res_list = com.example.navigation.util.JsonParser.parseRestaurantsJson(context,"restaurants.json");
        this.checkAndCreateFile();
    }

    public ArrayList<String> getFavoriteInfo(){
        ArrayList<String> favorite = new ArrayList<>();
        try{
            JSONArray jsonArray = json_obj.getJSONArray("userdata");
            for(int i=0; i<res_list.size(); ++i){
                String fav = jsonArray.getJSONObject(i).get("favorite").toString();
                favorite.add(fav);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return favorite;
    }

    private String getFavorite(int index){
        String result;
        try {
            JSONArray jsonArray = json_obj.getJSONArray("userdata");
            result = jsonArray.getJSONObject(index).get("favorite").toString();
        }
        catch (Exception e){
            e.printStackTrace();
            result = "0"; // 오류 시 0으로 처리
        }
        return result;
    }
    public boolean invertFavorite(RestaurantItem item){
        int i = 0;
        int cur = 0;
        for(; i<res_list.size(); i++){
            RestaurantItem curitem = res_list.get(i);
            if(curitem.getId().equals(item.getId())){
                break;
            }
        }
        try {
            JSONObject jsonObject = json_obj.getJSONArray("userdata").getJSONObject(i);
            String tmp = jsonObject.get("favorite").toString();
            cur = 1 - Integer.parseInt(tmp);
            jsonObject.put("favorite", cur);
            this.saveCurrentState();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return cur==1;
    }
    public boolean isFavorite(RestaurantItem item){
        int i = 0;
        for(; i<res_list.size(); i++){
            RestaurantItem curitem = res_list.get(i);
            if(curitem.getId().equals(item.getId())){
                break;
            }
        }
        return this.getFavorite(i).equals("1");
    }

    private void checkAndCreateFile(){
        File file = new File(context.getFilesDir(),filename);
        if(!file.exists()){
            this.createDefaultFile();
        }
        json_obj = readJsonFromFile(file);
    }

    private JSONObject readJsonFromFile(File file){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            return new JSONObject(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createDefaultFile(){
        String default_content = this.getDefaultContent();
        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(default_content.getBytes());
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getDefaultContent(){
        JSONArray userdata_list = new JSONArray();
        JSONObject content = new JSONObject();
        try {
            for (int i = 0; i < res_list.size(); ++i) {
                RestaurantItem item = res_list.get(i);
                JSONObject restaurant = new JSONObject();
                restaurant.put("id", item.getId());
                restaurant.put("favorite", "0");
                restaurant.put("review", new JSONObject());
                userdata_list.put(restaurant);
            }
            content.put("userdata", userdata_list);
            //content.put("len", res_list.size()); // For debugging ..
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    private void saveCurrentState(){
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(json_obj.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}