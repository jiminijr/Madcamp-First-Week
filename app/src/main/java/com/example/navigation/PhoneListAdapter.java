package com.example.navigation;

import android.content.Context;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class PhoneListAdapter extends BaseAdapter {
    private ArrayList<PhoneListItem> list;
    private final Context context;


    //Filter listFilter;

    public PhoneListAdapter(Context context, ArrayList<PhoneListItem> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public PhoneListItem getItem(int position){
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.phone_item, parent, false);
        }

        PhoneListItem cur = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.phone_name_view);
        TextView numTextView = convertView.findViewById(R.id.phone_num_view);

        nameTextView.setText(cur.getName());
        numTextView.setText(cur.getNum());

        return convertView;
    }


}