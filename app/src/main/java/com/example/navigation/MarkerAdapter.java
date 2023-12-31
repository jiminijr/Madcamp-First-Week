package com.example.navigation;

import android.view.View;

import androidx.annotation.NonNull;

import com.naver.maps.map.overlay.InfoWindow;

public class MarkerAdapter extends InfoWindow.DefaultViewAdapter{
    @NonNull
    @Override
    protected View getContentView(@NonNull InfoWindow infoWindow) {
        return null;
    }
}