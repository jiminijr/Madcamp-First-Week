package com.example.navigation.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navigation.PhoneListItem;
import com.example.navigation.R;
import com.example.navigation.PhoneListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends ListFragment {
    private HomeViewModel viewModel;
    private PhoneListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Adapter 적용
        viewModel = new HomeViewModel(getContext()); // Json 파일을 읽기 위해
        adapter = new PhoneListAdapter(getContext(), viewModel.getPhoneList()); // Json 읽어서 Adapter로 넘김
        setListAdapter(adapter); // Adapter 설정
        View view = super.onCreateView(inflater, container, savedInstanceState); // View

        // List의 각 Item Click 처리

        return view;

        /*
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        adapter = new PhoneListAdapter(getContext(), new ArrayList<>());

        ListView listView = view.findViewById(R.id.listview_phone);
        listView.setAdapter(adapter);
        viewModel.getPhoneList().observe(getViewLifecycleOwner(), newList -> {
            adapter.updateData(newList);
        });
        return view;
        */
    }
}