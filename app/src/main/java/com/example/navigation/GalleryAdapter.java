package com.example.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.widget.TextView;


import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;
    private List<ImageData> imageDataList;

    public GalleryAdapter(Context context, List<ImageData> imageDataList) {
        this.context = context;
        this.imageDataList = imageDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageData imageData = imageDataList.get(position);
        holder.imageView.setImageResource(imageData.getImageResId());
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        showImagePopup(imageDataList.get(position));
                    }
                }
            });
        }
    }

    private void showImagePopup(ImageData imageData) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup);

        ImageView imageView = dialog.findViewById(R.id.dialog_image_view);
        TextView nameView = dialog.findViewById(R.id.dialog_image_name);
        TextView featuresView = dialog.findViewById(R.id.dialog_image_features);
        TextView priceView = dialog.findViewById(R.id.dialog_image_price);
        ImageButton closeButton = dialog.findViewById(R.id.close_button);

        imageView.setImageResource(imageData.getImageResId());
        nameView.setText(imageData.getName());
        featuresView.setText(imageData.getFeatures());
        priceView.setText(imageData.getPrice());

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 팝업창 닫기
            }
        });

        dialog.show();
    }
}
