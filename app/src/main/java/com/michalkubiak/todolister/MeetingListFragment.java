package com.michalkubiak.todolister;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class MeetingListFragment extends MyFragment{

    private ListView listView;
    ArrayAdapter<String> adapter;

    public MeetingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meeting_list, container,
                false);
        listView = (ListView) rootView.findViewById(R.id.listview_meeting);

        String[] dummyData= new String[] { "Spotkanie z Damianem", "Spotkanie z Kasią", "Spotkanie z Marcinem",
                 };

        ArrayList<String> dummyDataList = new ArrayList<>(Arrays.asList(dummyData));

        adapter = new ArrayAdapter<>(getContext(),
                R.layout.row_layout, R.id.label, dummyDataList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "You clicked no: " + position, Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(String itemText){
        adapter.add(itemText);
        adapter.notifyDataSetChanged();
    }
}