package com.example.navigation;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.naver.maps.map.overlay.InfoWindow;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkerAdapter extends InfoWindow.DefaultViewAdapter{

    private final Context mcontext;
    private final ViewGroup mparent;
    private final MarkerItem mitem;
    public MarkerAdapter(@NonNull Context context, ViewGroup parent, MarkerItem item){
        super(context);
        mcontext = context;
        mparent = parent;
        mitem = item;
    }
    @NonNull
    @Override
    protected View getContentView(@NonNull InfoWindow infoWindow) {
        View view = (View) LayoutInflater.from(mcontext).inflate(R.layout.marker_view, mparent, false);

        TextView title = (TextView) view.findViewById(R.id.marker_name);
        ImageView main_image = (ImageView) view.findViewById(R.id.marker_main_image);
        TextView phone = (TextView) view.findViewById(R.id.marker_phone_text);
        TextView address = (TextView) view.findViewById(R.id.marker_address_text);
        TextView info = (TextView) view.findViewById(R.id.marker_info_text);
        TextView link = (TextView) view.findViewById(R.id.marker_link_text);

        main_image.setImageResource(mitem.getFoodimg());
        title.setText(mitem.getName());
        phone.setText(mitem.getNumber());
        address.setText(mitem.getAddress());
        info.setText(mitem.getInfo());
        link.setText(mitem.getLink());

        return view;
    }
}