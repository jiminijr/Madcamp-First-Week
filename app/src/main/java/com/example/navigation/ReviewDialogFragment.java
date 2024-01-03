package com.example.navigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;


public class ReviewDialogFragment extends DialogFragment {

    private EditText editTextReview;
    private RatingBar ratingBar;
    private ImageView imageViewSelectedImage;
    private RestaurantItem item;
    private UserJsonManager userJsonManager;
    private ReviewAdapter adapter;

    public ReviewDialogFragment(RestaurantItem item, UserJsonManager userJsonManager, ReviewAdapter adapter){
        this.item = item;
        this.userJsonManager = userJsonManager;
        this.adapter = adapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_review, null);

        editTextReview = view.findViewById(R.id.review_text_input);
        ratingBar = view.findViewById(R.id.review_rating);
        imageViewSelectedImage = view.findViewById(R.id.imageViewSelectedImage); // 이미지 입력 부분
        // 이미지 선택 버튼 리스너 설정
        imageViewSelectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               if(camera.resolveActivity(getActivity().getPackageManager())!=null){
                   startActivityForResult(camera, 0);
               }
            }
        });

        // 리뷰 제출 버튼 리스너 설정
        Button submitButton = view.findViewById(R.id.review_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review_text = editTextReview.getText().toString();
                float rating = ratingBar.getRating();
                Review review = new Review(review_text,"", rating);
                if(review_text.isEmpty()){
                    Toast.makeText(getContext(),"텍스트를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                userJsonManager.saveReview(review, item);
                adapter.update();
                dismiss();
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewSelectedImage.setImageBitmap(imageBitmap);
        }
    }
}