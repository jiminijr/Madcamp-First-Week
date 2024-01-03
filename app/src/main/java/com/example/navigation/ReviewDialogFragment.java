package com.example.navigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ImageView;
import android.widget.Button;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewDialogFragment extends DialogFragment {

    private EditText editTextReview;
    private RatingBar ratingBar;
    private ImageView imageViewSelectedImage;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_review, null);

        editTextReview = view.findViewById(R.id.editTextReview);
        ratingBar = view.findViewById(R.id.ratingBar);
        imageViewSelectedImage = view.findViewById(R.id.imageViewSelectedImage);

        // 이미지 선택 버튼 리스너 설정
        imageViewSelectedImage.setOnClickListener(v -> selectImage());

        // 리뷰 제출 버튼 리스너 설정
        Button submitButton = view.findViewById(R.id.buttonSubmitReview);
        submitButton.setOnClickListener(v -> submitReview());

        builder.setView(view);
        return builder.create();
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(android.content.intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageViewSelectedImage.setImageURI(selectedImageUri);
        }
    }

    private void submitReview() {
        String reviewText = editTextReview.getText().toString();
        float rating = ratingBar.getRating();
        // 이미지 URI를 문자열로 변환
        String imageUrl = imageViewSelectedImage.getTag() != null ? imageViewSelectedImage.getTag().toString() : "";

        try {
            JSONObject reviewJson = new JSONObject();
            reviewJson.put("reviewText", reviewText);
            reviewJson.put("rating", rating);
            reviewJson.put("imageUrl", imageUrl);

            // JSON 데이터를 내부 저장소에 저장
            saveReviewToFile(reviewJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveReviewToFile(JSONObject reviewJson) {
        try {
            // 파일 경로
            File file = new File(getContext().getFilesDir(), "userdata.json");
            JSONObject existingData;

            // 파일이 존재하면 기존 데이터 로드
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                existingData = new JSONObject(content);
            } else {
                // 파일이 없으면 새로운 JSON 객체 생성
                existingData = new JSONObject();
                existingData.put("userdata", new JSONArray());
            }

            // 기존 JSON 객체에 새 리뷰 데이터 추가
            JSONArray userdataArray = existingData.getJSONArray("userdata");
            JSONObject newUserData = new JSONObject();
            newUserData.put("id", String.valueOf(userdataArray.length() + 1));
            newUserData.put("favorite", 0);
            newUserData.put("review", reviewJson);

            userdataArray.put(newUserData);

            // JSON 데이터를 파일에 저장
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(existingData.toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}