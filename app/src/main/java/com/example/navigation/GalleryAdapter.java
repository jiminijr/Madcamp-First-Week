package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;

import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private Fragment currentFragment;
    private List<RestaurantItem> restaurantList;
    private UserJsonManager userJsonManager;

    private List<RestaurantItem> unique_tag_restaurantList;

    public GalleryAdapter(Context context, List<RestaurantItem> restaurantList, Fragment currentFragment, UserJsonManager manager) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.currentFragment = currentFragment;
        this.Delete_Duplicate();
        this.userJsonManager = manager;
    }

    private void Delete_Duplicate(){
        unique_tag_restaurantList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        for(RestaurantItem item : restaurantList){
            String tag = item.getTag();
            if(!tags.contains(tag)){
                unique_tag_restaurantList.add(item);
                tags.add(tag);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantItem restaurantItem = unique_tag_restaurantList.get(position);
        int foodImgResId = context.getResources().getIdentifier("food" + (position + 1), "drawable", context.getPackageName());
        holder.imageView.setImageResource(foodImgResId);
        holder.tagTextView.setText(restaurantItem.getTag()); // 태그 설정
    }

    @Override
    public int getItemCount() {
        return unique_tag_restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tagTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            tagTextView = itemView.findViewById(R.id.text_view_tag); // 태그 표시용 TextView 초기화

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        showRestaurantPopup(restaurantList.get(position));
                    }
                }
            });
        }
    }

    private void showRestaurantPopup(RestaurantItem clickedItem) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);

        ImageView foodImageView = dialog.findViewById(R.id.dialog_food_image_view);
        foodImageView.setImageResource(clickedItem.getFoodImg());

        // 태그 표시를 위한 TextView
        TextView tagTextView = dialog.findViewById(R.id.text_view_tag);
        tagTextView.setText(clickedItem.getTag());

        String tag = clickedItem.getTag();
        List<RestaurantItem> filteredList = getRestaurantsWithTag(tag);

        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_recycler_view);
        RestaurantAdapter adapter = new RestaurantAdapter(context, (ArrayList<RestaurantItem>) filteredList, currentFragment, userJsonManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ImageButton closeButton = dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> dialog.dismiss());

        NavController navController = NavHostFragment.findNavController(currentFragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                    dialog.dismiss();
                });
        dialog.show();
    }

    private List<RestaurantItem> getRestaurantsWithTag(String tag) {
        List<RestaurantItem> filteredList = new ArrayList<>();
        for (RestaurantItem item : restaurantList) {
            if (item.getTag().equals(tag)) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}
