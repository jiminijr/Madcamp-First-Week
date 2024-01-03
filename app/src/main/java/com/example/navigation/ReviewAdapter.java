package com.example.navigation;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private UserJsonManager userJsonManager;
    private RestaurantItem item;
    private List<Review> reviewList;

    public ReviewAdapter(UserJsonManager userJsonManager, RestaurantItem item) {
        this.context = context;
        this.reviewList = reviewList;
        this.userJsonManager = userJsonManager;
        this.item = item;
        this.update();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView reviewTextView;
        ImageView reviewImageView;
        RatingBar reviewRatingBar;

        public ViewHolder(View view) {
            super(view);
            reviewTextView = view.findViewById(R.id.review_text);
            reviewImageView = view.findViewById(R.id.review_image);
            reviewRatingBar = view.findViewById(R.id.review_rating);
        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.reviewTextView.setText(review.getReviewText());
        holder.reviewRatingBar.setRating(review.getRating());
    }


    public void update(){
        reviewList = userJsonManager.getReviewList(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
