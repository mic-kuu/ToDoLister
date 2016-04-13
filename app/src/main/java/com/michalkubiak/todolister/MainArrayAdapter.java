package com.michalkubiak.todolister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.michalkubiak.todolister.Models.ListItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by michal on 30.03.16.
 */
public class MainArrayAdapter extends BaseAdapter implements ListAdapter {
    private List<ListItem> list = new ArrayList<>();
    private Context context;
    private MainDatabaseHelper databaseHelper;
    private int fragmentType;

    public static final int SHOPPING = 0;
    public static final int MEETING = 1;
    public static final int REMINDER = 2;




    public MainArrayAdapter(List<ListItem> list, Context context, int fragmentType) {
        this.list = list;
        this.context = context;
        this.fragmentType = fragmentType;
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


        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_layout, null);
        }

        TextView itemText = (TextView) view.findViewById(R.id.rowTextItem);
        itemText.setText(list.get(position).itemText);


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.rowCheckBox);
        checkBox.setChecked(false);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    databaseHelper = MainDatabaseHelper.getInstance(context);
                    switch (fragmentType) {
                        case SHOPPING:
                            databaseHelper.deleteShoppingItem(list.get(position).id);
                            break;
                        case MEETING:
                            databaseHelper.deleteMeetingItem(list.get(position).id);
                            break;
                        case REMINDER:
                            databaseHelper.deleteReminderItem(list.get(position).id);
                            break;
                    }

                    list.remove(position);
                    notifyDataSetChanged();

                }
            }
        });
        return view;
    }



}


