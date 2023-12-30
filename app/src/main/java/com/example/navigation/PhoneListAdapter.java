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

public class PhoneListAdapter extends BaseAdapter implements Filterable {
    private ArrayList<PhoneListItem> list;
    private final Context context;

    Filter listFilter;

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

    @Override
    public Filter getFilter(){
        if(listFilter == null){
            listFilter = new ListFilter();
        }
        return listFilter;
    }

    private class ListFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0){
                results.values = list;
                results.count = list.size();
            } else{
                // 만약 text가 입력된다면..
                ArrayList<PhoneListItem> filtered = new ArrayList<PhoneListItem>();

                for(PhoneListItem item : list){
                    if(item.getName().toLowerCase().contains(constraint.toString().toLowerCase())){ // 검색조건
                        filtered.add(item);
                    }
                }
                results.values = filtered;
                results.count = filtered.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            ArrayList<PhoneListItem> filtered = (ArrayList<PhoneListItem>) results.values;

            if(results.count > 0){
                notifyDataSetChanged();
            } else{
                notifyDataSetInvalidated();
            }
        }
    }
}