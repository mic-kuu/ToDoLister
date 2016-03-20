package com.michalkubiak.todolister;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ShoppingListFragment extends Fragment{

    private ListView listView;
    ArrayAdapter<String> adapter;

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
                "Jajka", "Pieczeń", "Pomidor", "Ogórek", "Makaron",
                "Dżem", "Herbata" };

        adapter = new ArrayAdapter<>(getContext(),
                R.layout.row_layout, R.id.label, dummyData);

        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(){
        adapter.add("Nowa rzecz do kupienia");
        adapter.notifyDataSetChanged();
    }

}