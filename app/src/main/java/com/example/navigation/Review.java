package com.example.navigation;

public class Review {

    private String reviewText;
    private String imageUrl;
    private float rating;

    // 생성자
    public Review(String reviewText, String imageUrl, int rating) {
        this.reviewText = reviewText;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    // Getter 메소드
    public String getReviewText() {
        return reviewText;
    }

    public float getRating() {
        return rating;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
