package com.michalkubiak.todolister;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private MeetingListFragment meetingListFragment;
    private ReminderListFragment reminderListFragment;
    private ShoppingListFragment shoppingListFragment;
    private FragmentManager fragmentManager;

    private int[] tabIcons = {
            R.drawable.ic_action_action_shopping_cart,
            R.drawable.ic_action_social_group,
            R.drawable.ic_action_social_notifications
    };

    private String shoppingListTag;
    private String meetingListTag;
    private String reminderListTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = tabLayout.getSelectedTabPosition();

                switch (position) {
                    case 0:
                        addToList(shoppingListTag);
                        break;
                    case 1:
                        addToList(meetingListTag);
                        break;
                    case 2:
                        addToList(reminderListTag);
                        break;

                }

            }

        });
    }

    private void addToList(String tag){
        fragmentManager = getSupportFragmentManager();
        MyFragment fragment = (MyFragment) fragmentManager.findFragmentByTag(tag);
        fragment.addItem();
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
            // get the tags set by FragmentPagerAdapter
            switch (position) {
                case 0:
                    shoppingListTag = createdFragment.getTag();
                    break;
                case 1:
                    meetingListTag = createdFragment.getTag();
                    break;
                case 2:
                    reminderListTag =createdFragment.getTag();
                    break;

            }
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