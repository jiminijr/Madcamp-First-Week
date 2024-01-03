package com.example.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_reviews_layout);

        ImageButton reviewButton = findViewById(R.id.review_button);
        ImageButton closeButton = findViewById(R.id.close_button);

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 리뷰 등록창 열기
                openReviewDialog();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 팝업창 닫기
                finish(); // 현재 Activity를 종료
            }
        });
    }

    private void openReviewDialog() {
        // 리뷰 등록 다이얼로그 생성 및 표시
        ReviewDialogFragment dialogFragment = new ReviewDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "ReviewDialog");
    }
}
