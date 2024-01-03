package com.example.navigation;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements Filterable {

    private final Context context;
    private final ArrayList<RestaurantItem> restaurantList;
    private Fragment currentFragment;
    private ArrayList<RestaurantItem> fav_filtered_restaurantList;
    private ArrayList<RestaurantItem> filtered_restaurantList;

    private int state;

    private UserJsonManager userJsonManager;
    // 클릭 이벤트 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private List<Review> createDummyReviews() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("맛있어요!", "asd5", 2));
        reviews.add(new Review("좋았습니다.", "asdasd4", 4));
        // 여기에 더 많은 리뷰를 추가할 수 있습니다.
        return reviews;
    }
    private OnItemClickListener listener;

    public RestaurantAdapter(Context context, ArrayList<RestaurantItem> restaurantList, Fragment currentFragment, UserJsonManager manager) {
        this.context = context;
        this.state = 0;
        this.restaurantList = restaurantList;
        this.fav_filtered_restaurantList = restaurantList;
        this.filtered_restaurantList = restaurantList;
        this.currentFragment = currentFragment;
        this.userJsonManager = manager;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder holder, int position) {
        RestaurantItem item = filtered_restaurantList.get(position);

        holder.imageView.setImageResource(item.getOutImg());
        holder.nameTextView.setText(item.getName());
        holder.numberTextView.setText(item.getNumber());
        holder.addressTextView.setText(item.getAddress());
        holder.infoTextView.setText(item.getInfo());

        // 즐겨찾기 정보로 image view 설정
        boolean fav = userJsonManager.isFavorite(item);
        holder.favorite.setImageResource(fav? R.drawable.ic_heart_filled_black_24dp : R.drawable.ic_heart_unfilled_black_24dp);

        // 디버그 로그: 상세 정보 뷰의 현재 가시성 상태 출력
        Log.d("RestaurantAdapter", "Position " + position + " isExpanded: " + item.isExpanded());
        // 상세 정보 뷰 가시성 설정
        holder.detailsView.setVisibility(item.isExpanded() ? View.VISIBLE : View.GONE);

        if (item.isExpanded()) {
            // 상세 정보 레이아웃 로드
            View detailsView = LayoutInflater.from(context).inflate(R.layout.details_restaurant, holder.detailsView, false);
            holder.detailsView.removeAllViews();
            holder.detailsView.addView(detailsView);

            // 메뉴 데이터 바인딩
            bindMenuDataToView(detailsView, item);
            holder.detailsView.setVisibility(View.VISIBLE);
        } else {
            holder.detailsView.setVisibility(View.GONE);
            holder.detailsView.removeAllViews();
        }

        // 아이템 클릭 리스너
        holder.itemView.setOnClickListener(v -> {
            item.setExpanded(!item.isExpanded());
            notifyItemChanged(holder.getBindingAdapterPosition());
        });

        holder.favorite.setOnClickListener(v -> {
            boolean currentFav = userJsonManager.invertFavorite(item);
            holder.favorite.setImageResource(currentFav? R.drawable.ic_heart_filled_black_24dp : R.drawable.ic_heart_unfilled_black_24dp);
        });
    }

    private void bindMenuDataToView(View detailsView, RestaurantItem item) {
        if (!item.getMenuList().isEmpty()) {
            for (int i = 0; i < item.getMenuList().size(); i++) {
                Menu menu = item.getMenuList().get(i);
                if (i == 0) {
                    updateMenuLayout(detailsView, menu, R.id.frame1, R.id.image1, R.id.text1, R.id.text1_1);
                } else if (i == 1) {
                    updateMenuLayout(detailsView, menu, R.id.frame2, R.id.image2, R.id.text2, R.id.text2_1);
                } else if (i == 2) {
                    updateMenuLayout(detailsView, menu, R.id.frame3, R.id.image3, R.id.text3, R.id.text3_1);
                }
            }
        }
        ImageView phoneCallIcon = detailsView.findViewById(R.id.icon_phone_call); // details_restaurant 레이아웃에서 아이콘 찾기
        if (phoneCallIcon != null) {
            phoneCallIcon.setOnClickListener(v -> {
                // 전화 걸기 인텐트 실행
                String phoneNumber = item.getNumber(); // 전화번호 가져오기
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                detailsView.getContext().startActivity(callIntent);
            });
        }

        ImageView maplinkIcon = detailsView.findViewById(R.id.icon_map);
        if (maplinkIcon != null) {
            maplinkIcon.setOnClickListener(v -> {
                // map에 연결 -> Navcontroller 가져와서 map 이동 -> Camera를 대응하는 것으로 설정 -> info window activate
                // item 전달
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", item);

                NavController navController = NavHostFragment.findNavController(currentFragment);
                navController.popBackStack();
                navController.navigate(R.id.navigation_notifications, bundle);
            });
        }
        ImageView reviewIcon = detailsView.findViewById(R.id.review);
        if (reviewIcon != null) {
            reviewIcon.setOnClickListener(v -> {
                Dialog reviewsDialog = new Dialog(context);
                reviewsDialog.setContentView(R.layout.popup_reviews_layout);

                // 여기에서 ID를 'reviewRecyclerView'로 변경
                RecyclerView recyclerView = reviewsDialog.findViewById(R.id.reviewRecyclerView);
                if (recyclerView != null) {
                    List<Review> reviewList = createDummyReviews();
                    ReviewAdapter adapter = new ReviewAdapter(reviewList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    reviewsDialog.show();
                } else {
                    Log.e("RestaurantAdapter", "RecyclerView is null");
                }
            });
        }
    }

    private void updateMenuLayout(View detailsView, Menu menu, int frameId, int imageId, int textId, int textPriceId) {
        FrameLayout frameLayout = detailsView.findViewById(frameId);
        ImageView imageView = frameLayout.findViewById(imageId);
        TextView textView = frameLayout.findViewById(textId);
        TextView textPriceView = frameLayout.findViewById(textPriceId);

        imageView.setImageResource(menu.getMenuImgResId());
        textView.setText(menu.getMenuName());
        textPriceView.setText(menu.getMenuPrice() + "원");
    }

    public void setState(int state){
        this.state = state;
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            private int fav_state = 0; // 1이면 fav, 0이면 fav 아님, 2면 state 변경없이 진행
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if(state != 2){
                    fav_state = state;
                }

                String query = constraint.toString();

                if (fav_state == 0) {
                    fav_filtered_restaurantList = restaurantList;
                }
                else if(fav_state == 1){
                    ArrayList<RestaurantItem> tmp_filtered_restaurantList = new ArrayList<>();
                    ArrayList<String> fav_info = userJsonManager.getFavoriteInfo();
                    for(int i=0; i<restaurantList.size(); ++i){
                        String f = fav_info.get(i);
                        if(f.equals("1")){
                            tmp_filtered_restaurantList.add(restaurantList.get(i));
                        }
                    }
                    fav_filtered_restaurantList = tmp_filtered_restaurantList;
                }

                if(query.isEmpty()){
                    filtered_restaurantList = fav_filtered_restaurantList;
                }
                else {
                    ArrayList<RestaurantItem> tmp_filtered_restaurantList = new ArrayList<>();
                    for (RestaurantItem item : fav_filtered_restaurantList) {
                        if (item.getName().toLowerCase().contains(query.toLowerCase())
                                || item.getAddress().toLowerCase().contains(query.toLowerCase())) {
                            tmp_filtered_restaurantList.add(item);
                        }
                    }
                    filtered_restaurantList = tmp_filtered_restaurantList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_restaurantList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered_restaurantList = (ArrayList<RestaurantItem>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public void gather_favorite(){
        ArrayList<String> favorite = userJsonManager.getFavoriteInfo();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filtered_restaurantList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView favorite;
        TextView nameTextView;
        TextView numberTextView;
        TextView addressTextView;
        TextView infoTextView;
        ViewGroup detailsView; // 상세 정보 뷰 멤버 변수 선언
        ImageView phoneCallIcon; // 전화 아이콘 추가
        Button reviewButton;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image_view);
            nameTextView = itemView.findViewById(R.id.restaurant_name_view);
            numberTextView = itemView.findViewById(R.id.restaurant_number_view);
            addressTextView = itemView.findViewById(R.id.restaurant_address_view);
            infoTextView = itemView.findViewById(R.id.restaurant_info_view);
            detailsView = itemView.findViewById(R.id.details_container); // 상세 정보 뷰 초기화
            reviewButton = itemView.findViewById(R.id.review); // 리뷰 버튼 초기화
            favorite = itemView.findViewById(R.id.favorite);

        }
    }
}