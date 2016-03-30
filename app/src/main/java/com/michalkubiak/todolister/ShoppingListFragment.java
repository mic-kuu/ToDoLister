package com.michalkubiak.todolister;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class ShoppingListFragment extends MyFragment{

    private ListView listView;
    private MainArrayAdapter adapter;
    private ArrayList<String> dummyDataList;

    public ShoppingListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shopping_list, container,
                false);
        listView = (ListView) rootView.findViewById(R.id.listview_shopping);

        String[] dummyData= new String[] { "Mleko", "Płatki", "Pieczywo",
                "Jajka", "Pieczeń", "Pomidor", "Ogórek", "Makaron"};

        dummyDataList = new ArrayList<>(Arrays.asList(dummyData));

        adapter = new MainArrayAdapter(dummyDataList, getContext());
        listView.setAdapter(adapter);




        return rootView;
    }

    public void addItem(String itemText){

        dummyDataList.add(itemText);
        adapter.notifyDataSetChanged();

    }

}