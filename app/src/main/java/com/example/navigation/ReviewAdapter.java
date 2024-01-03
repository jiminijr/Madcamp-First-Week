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

import com.bumptech.glide.Glide;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
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

        if (review.getImageUrl() != null && !review.getImageUrl().isEmpty()) {
            if (holder.reviewImageView.isAttachedToWindow()) { // 뷰가 화면에 첨부되었는지 확인
                Glide.with(context)
                        .load(Uri.parse(review.getImageUrl()))
                        .error(R.drawable.ic_search_black_24dp)
                        .into(holder.reviewImageView);
            }
        } else {
            holder.reviewImageView.setImageResource(R.drawable.ic_cross_black_24dp);
        }
    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
