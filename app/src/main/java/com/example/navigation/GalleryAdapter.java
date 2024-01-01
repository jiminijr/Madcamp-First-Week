package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<com.example.navigation.RestaurantItem> restaurantList;

    public GalleryAdapter(Context context, List<com.example.navigation.RestaurantItem> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantItem restaurantItem = restaurantList.get(position);
        // 'food image 1'부터 'food image 20'까지의 이미지 리소스 ID를 설정
        int foodImgResId = context.getResources().getIdentifier("food" + (position + 1), "drawable", context.getPackageName());
        holder.imageView.setImageResource(foodImgResId);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        showRestaurantPopup(restaurantList.get(position)); // 수정된 부분
                    }
                }
            });
        }
    }


    private void showRestaurantPopup(RestaurantItem clickedItem) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);

        // 팝업에 클릭된 음식 이미지 표시
        ImageView foodImageView = dialog.findViewById(R.id.dialog_food_image_view);
        foodImageView.setImageResource(clickedItem.getFoodImg());

        // 팝업에 태그와 일치하는 레스토랑 목록 표시
        String tag = clickedItem.getTag();
        List<RestaurantItem> filteredList = getRestaurantsWithTag(tag);

        // 필터링된 레스토랑 목록을 표시하는 RecyclerView 설정
        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_recycler_view);
        RestaurantAdapter adapter = new RestaurantAdapter(context, (ArrayList<RestaurantItem>) filteredList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ImageButton closeButton = dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dialog.dismiss()); // 람다 표현식으로 변경

        dialog.show();
    }

    private List<RestaurantItem> getRestaurantsWithTag(String tag) {
        List<RestaurantItem> filteredList = new ArrayList<>();
        for (RestaurantItem item : this.restaurantList) { // 클래스의 멤버 변수를 사용
            if (item.getTag().equals(tag)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}