package com.example.navigation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<RestaurantItem> restaurantList;

    // 클릭 이벤트 리스너 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener listener;

    public RestaurantAdapter(Context context, ArrayList<RestaurantItem> restaurantList) {
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder holder, int position) {
        RestaurantItem item = restaurantList.get(position);

        holder.imageView.setImageResource(item.getOutImg());
        holder.nameTextView.setText(item.getName());
        holder.numberTextView.setText(item.getNumber());
        holder.addressTextView.setText(item.getAddress());
        holder.infoTextView.setText(item.getInfo());

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
    }

    private void updateMenuLayout(View detailsView, Menu menu, int frameId, int imageId, int textId, int textPriceId) {
        FrameLayout frameLayout = detailsView.findViewById(frameId);
        ImageView imageView = frameLayout.findViewById(imageId);
        TextView textView = frameLayout.findViewById(textId);
        TextView textPriceView = frameLayout.findViewById(textPriceId);

        imageView.setImageResource(menu.getMenuImgResId());
        textView.setText(menu.getMenuName());
        textPriceView.setText(menu.getMenuPrice());
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView numberTextView;
        TextView addressTextView;
        TextView infoTextView;
        ViewGroup detailsView; // 상세 정보 뷰 멤버 변수 선언

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurant_image_view);
            nameTextView = itemView.findViewById(R.id.restaurant_name_view);
            numberTextView = itemView.findViewById(R.id.restaurant_number_view);
            addressTextView = itemView.findViewById(R.id.restaurant_address_view);
            infoTextView = itemView.findViewById(R.id.restaurant_info_view);
            detailsView = itemView.findViewById(R.id.details_container); // 상세 정보 뷰 초기화

        }
    }
}
