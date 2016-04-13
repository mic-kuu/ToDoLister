package com.michalkubiak.todolister;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.michalkubiak.todolister.Models.ListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MeetingListFragment extends MyFragment{

    private ListView listView;
    private MainArrayAdapter adapter;
    private List<ListItem> listItems;
    private MainDatabaseHelper databaseHelper;

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
        listView.setEmptyView(rootView.findViewById(R.id.emptyView_meeting));

        databaseHelper = MainDatabaseHelper.getInstance(getContext());
        listItems = new ArrayList<>();
        listItems = databaseHelper.getNewMeetingItems();

        adapter = new MainArrayAdapter(listItems, getContext(), MainArrayAdapter.MEETING);
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

        databaseHelper.addMeetingItem(newListItem);
        listItems.clear();
        listItems.addAll(databaseHelper.getNewMeetingItems());

        adapter.notifyDataSetChanged();
    }
}