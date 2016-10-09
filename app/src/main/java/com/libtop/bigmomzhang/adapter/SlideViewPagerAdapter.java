package com.libtop.bigmomzhang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.libtop.bigmomzhang.SearchResultFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by LianTu on 2016-10-8.
 */

public class SlideViewPagerAdapter extends FragmentPagerAdapter
{

    private List<String> mTitles = new ArrayList<>();

    public SlideViewPagerAdapter(FragmentManager fm,String[] strings) {
        super(fm);
        Collections.addAll(mTitles,strings);
    }

    public void addTag(String title){
        mTitles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
       return SearchResultFragment.getInstance(mTitles.get(position));
    }
}
