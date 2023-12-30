package com.example.navigation;

import android.content.Context;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private ArrayList<com.example.navigation.RestaurantItem> restaurantList;

    public RestaurantAdapter(Context context, ArrayList<RestaurantItem> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder holder, int position) {
        RestaurantItem item = restaurantList.get(position);
        holder.imageView.setImageResource(item.getOutImg()); // 이미지 설정
        holder.nameTextView.setText(item.getName());
        holder.numberTextView.setText(item.getNumber());
        holder.addressTextView.setText(item.getAddress());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView numberTextView;
        TextView addressTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image_view); // ID 수정
            nameTextView = itemView.findViewById(R.id.restaurant_name_view); // ID 수정
            numberTextView = itemView.findViewById(R.id.restaurant_number_view); // ID 수정
            addressTextView = itemView.findViewById(R.id.restaurant_address_view); // ID 수정
        }
    }
}
