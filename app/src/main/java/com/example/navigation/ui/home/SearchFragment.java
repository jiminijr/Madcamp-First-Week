package com.example.navigation.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.navigation.R;
import com.example.navigation.RestaurantAdapter;

public class SearchFragment extends Fragment {

    private RestaurantAdapter adapter;
    private TextView search ;
    public SearchFragment(RestaurantAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        adapter.setSearchState(1);
        // 입력 처리 + claer 버튼 조절
        search = (TextView) view.findViewById(R.id.search_text);
        ImageButton clear = (ImageButton) view.findViewById(R.id.search_text_clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText(null);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                clear.setVisibility(s.toString().length() > 0? View.VISIBLE:View.GONE);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        adapter.setSearchState(0);
        search.setText(null);
    }
}