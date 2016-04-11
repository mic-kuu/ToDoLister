package com.michalkubiak.todolister;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private FragmentManager fragmentManager;

    //TODO: make callbacks to delete this field
    private String itemText;
    private ArrayList<String> tabTags = new ArrayList<>();

    private int[] tabIcons = {
            R.drawable.ic_action_action_shopping_cart,
            R.drawable.ic_action_social_group,
            R.drawable.ic_action_social_notifications
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLayoutSkeleton();
        setupTabIcons();
        setupFab();


    }


    private void setupLayoutSkeleton() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showInputDialog();
            }

        });
    }

    private void addToList(String tag) {

        fragmentManager = getSupportFragmentManager();
        MyFragment fragment = (MyFragment) fragmentManager.findFragmentByTag(tag);
        fragment.addItem(itemText);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogStyle);
        builder.setTitle(R.string.dialog_additemtitle);
        final EditText input = new EditText(MainActivity.this);

        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(getString(R.string.all_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = tabLayout.getSelectedTabPosition();
                itemText = input.getText().toString();

                if (itemText != null) addToList(tabTags.get(position));
            }
        });
        builder.setNegativeButton(getString(R.string.all_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ShoppingListFragment(), null);
        adapter.addFragment(new MeetingListFragment(), null);
        adapter.addFragment(new ReminderListFragment(), null);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new ShoppingListFragment();
                case 1:
                    return new MeetingListFragment();
                case 2:
                    return new ReminderListFragment();
                default:
                    return null;
            }

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);

            tabTags.add(position, createdFragment.getTag());

            return createdFragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}