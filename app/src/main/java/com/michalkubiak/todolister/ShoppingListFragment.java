package com.michalkubiak.todolister;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.michalkubiak.todolister.Models.ListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShoppingListFragment extends MyFragment{

    private ListView listView;
    private MainArrayAdapter adapter;    private List<ListItem> listItems;

    private MainDatabaseHelper databaseHelper;

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
        listView.setEmptyView(rootView.findViewById(R.id.emptyView_shopping));

        databaseHelper = MainDatabaseHelper.getInstance(getContext());
        listItems = new ArrayList<>();
        listItems = databaseHelper.getNewShoppingItems();

        adapter = new MainArrayAdapter(listItems, getContext());
        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(String itemText){

        Date date = new Date();
        ListItem newListItem = new ListItem();
        newListItem.itemText = itemText;
        newListItem.isCheked = 0;
        newListItem.timeCreated = (int) date.getTime();
        newListItem.timeCompleted = 0;

        databaseHelper.addShoppingItem(newListItem);
        listItems.clear();
        listItems.addAll(databaseHelper.getNewShoppingItems());

        adapter.notifyDataSetChanged();

    }



}