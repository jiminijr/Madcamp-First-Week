

package com.example.navigation;
import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;


public class ReviewDialogFragment extends DialogFragment {

    private EditText editTextReview;
    private RatingBar ratingBar;
    private ImageView imageViewSelectedImage;
    private RestaurantItem item;
    private UserJsonManager userJsonManager;
    private ReviewAdapter adapter;
    private ActivityResultLauncher<Intent> cameraLauncher;

    private Uri imageUri;

    private ActivityResultLauncher<Intent> galleryLauncher;

    public ReviewDialogFragment(RestaurantItem item, UserJsonManager userJsonManager, ReviewAdapter adapter){
        this.item = item;
        this.userJsonManager = userJsonManager;
        this.adapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 카메라로부터 이미지를 가져오는 ActivityResultLauncher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageViewSelectedImage.setImageBitmap(imageBitmap);
                        // 이미지 URI 저장 로직 추가 (임시 파일 저장 등)
                        userJsonManager.saveBitMap(imageBitmap);
                    }
                }
        );

        // 갤러리에서 이미지를 가져오는 ActivityResultLauncher
        galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    imageViewSelectedImage.setImageURI(imageUri);
                }
            }
        );
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
        imageViewSelectedImage.setOnClickListener(v -> {
            showImageChoiceDialog();
        });

        // 리뷰 제출 버튼 리스너 설정
        Button submitButton = view.findViewById(R.id.review_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reviewText = editTextReview.getText().toString();
                float rating = ratingBar.getRating();
                String imageUrl = (imageUri != null) ? imageUri.toString() : "";

                if(reviewText.isEmpty()){
                    Toast.makeText(getContext(), "텍스트를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }

                Review review = new Review(reviewText, imageUrl, rating);
                userJsonManager.saveReview(review, item);
                adapter.update();
                dismiss();
            }
        });


        builder.setView(view);
        return builder.create();
    }

    // 권한 요청 메서드
    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 0);
        } else {
            openCamera();
        }
    }

    // 사용자의 권한 요청 응답 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 ) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 카메라 앱 실행
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void showImageChoiceDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                requestCameraPermission();
            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(pickPhoto);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}