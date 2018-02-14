package com.android.llc.proringer.pro.proringerpro.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.llc.proringer.pro.proringerpro.fragmnets.getstarted.ScreenSlidePageFragment;

import java.util.ArrayList;

/**
 * Created by su on 2/14/18.
 */

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<String> stringArrayList;

    public ScreenSlidePagerAdapter(FragmentManager fm,ArrayList<String> stringArrayList) {
        super(fm);
        this.stringArrayList=stringArrayList;
    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        String url = stringArrayList.get(position);
        bundle.putString("url", url);
        ScreenSlidePageFragment screenSlidePageFragment=new ScreenSlidePageFragment();
        screenSlidePageFragment.setArguments(bundle);

        return screenSlidePageFragment;
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }
}
