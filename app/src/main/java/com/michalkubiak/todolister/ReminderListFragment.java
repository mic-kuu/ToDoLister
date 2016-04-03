package com.michalkubiak.todolister;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class ReminderListFragment extends MyFragment{

    private ListView listView;
    private MainArrayAdapter adapter;
    private ArrayList<String> dummyDataList;

    public ReminderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder_list, container,
                false);
        listView = (ListView) rootView.findViewById(R.id.listview_reminder);
        listView.setEmptyView(rootView.findViewById(R.id.emptyView_reminder));

        dummyDataList = new ArrayList<>();
        adapter = new MainArrayAdapter(dummyDataList, getContext());
        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(String itemText){
        dummyDataList.add(itemText);
        adapter.notifyDataSetChanged();
    }



}