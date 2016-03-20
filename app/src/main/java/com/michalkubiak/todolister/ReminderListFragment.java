package com.michalkubiak.todolister;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ReminderListFragment extends Fragment{

    private ListView listView;
    ArrayAdapter<String> adapter;

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

        String[] dummyData= new String[] { "Odwiedź babcię", "Zrób pranie", "Wyślij maila do Anety",
                "Przeczytaj 'Wojnę i Pokój'", "Zapłać ratę kredytu" };
        // use your custom layout
        adapter = new ArrayAdapter<>(getContext(),
                R.layout.row_layout, R.id.label, dummyData);

        listView.setAdapter(adapter);

        return rootView;
    }

    public void addItem(){
        adapter.add("Nowe przypomnienie");
        adapter.notifyDataSetChanged();
    }

}