package com.example.taskboxx;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Shubham Maheshwari on 13-04-2017.
 */

public class viewpageradapter extends FragmentStatePagerAdapter {


    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<String>tabTitles = new ArrayList<>();

    public void addFragments(Fragment fragments, String tabTitles){
        this.fragments.add(fragments);
        this.tabTitles.add(tabTitles);
    }


    public viewpageradapter(FragmentManager fragmentManager){
        super(fragmentManager);

    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles.get(position);
    }
}
