package com.android.peter.hs_friend_quest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PTTFragment();
            case 1:
                return new PTTFragment();
            case 2:
                return new PTTFragment();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
