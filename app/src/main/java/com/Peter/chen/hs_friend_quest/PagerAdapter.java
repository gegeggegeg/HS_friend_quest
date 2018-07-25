package com.Peter.chen.hs_friend_quest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {


    private static ArrayList<Fragment> fragmentlist;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentlist = new ArrayList<>();
        fragmentlist.add(new PTTFragment());
        fragmentlist.add(new RedditFragment());
        fragmentlist.add(new PwnFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }
}
