package com.example.navigation.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation.PhoneListAdapter;
import com.example.navigation.R;


public class HomeFragment extends ListFragment {
    private HomeViewModel viewModel;
    private PhoneListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState); // View
        viewModel = new HomeViewModel(getContext()); // Json 파일을 읽기 위해
        adapter = new PhoneListAdapter(getContext(), viewModel.getPhoneList()); // Json 읽어서 Adapter로 넘김
        setListAdapter(adapter); // Adapter 설정

        /*
        ListView listview = view.findViewById(R.id.listview_phone);

        EditText text = (EditText) listview.findViewById(R.id.search_text);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString();
                if(filterText.length() > 0){
                    listview.setFilterText(filterText);
                } else{
                    listview.clearTextFilter();
                }
            }
        });
         */
        return view;

    }
}