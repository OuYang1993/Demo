package com.ouyang.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by oylz on 2015/5/27.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    List<Fragment> list;

    public PagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }


}
