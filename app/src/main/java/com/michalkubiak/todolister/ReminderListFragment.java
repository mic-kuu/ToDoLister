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
    private ArrayAdapter<String> adapter;
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

        String[] dummyData = new String[] { "Odwiedź babcię", "Zrób pranie", "Wyślij maila do Anety",
                "Przeczytaj 'Wojnę i Pokój'", "Zapłać ratę kredytu" };

        dummyDataList = new ArrayList<>(Arrays.asList(dummyData));


        adapter = new ArrayAdapter<>(getContext(),
                R.layout.row_layout, R.id.rowTextItem, dummyDataList);

        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(String itemText){
        dummyDataList.add(itemText);
        adapter.notifyDataSetChanged();
    }



}