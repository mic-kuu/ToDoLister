package com.michalkubiak.todolister;


import android.widget.ArrayAdapter;

/**
 * Created by michal on 24.03.16.
 */
public abstract class MyFragment extends android.support.v4.app.Fragment {

    public abstract void addItem(String itemText);
}
