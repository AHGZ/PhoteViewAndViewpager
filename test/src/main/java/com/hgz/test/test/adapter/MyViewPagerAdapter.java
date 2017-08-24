package com.hgz.test.test.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hgz.test.test.fragment.TuijianFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter{

    private FragmentManager fm;
    private List<String> list;
    public MyViewPagerAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.fm=fm;
        this.list= list;
    }

    @Override
    public Fragment getItem(int position) {
        TuijianFragment fragment = new TuijianFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text",list.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
