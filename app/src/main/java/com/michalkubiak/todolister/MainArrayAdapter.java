package com.michalkubiak.todolister;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by michal on 30.03.16.
 */
public class MainArrayAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<>();
    private Context context;



    public MainArrayAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        //TODO: Fix automatic cheking of next item
        Log.d("MICHAL", "recived position: " + position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_layout, null);
        }

        TextView itemText = (TextView) view.findViewById(R.id.rowTextItem);
        itemText.setText(list.get(position));


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.rowCheckBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}