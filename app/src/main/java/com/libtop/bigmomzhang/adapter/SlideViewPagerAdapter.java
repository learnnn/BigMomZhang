package com.libtop.bigmomzhang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.libtop.bigmomzhang.App;
import com.libtop.bigmomzhang.SearchResultFragment;
import com.libtop.bigmomzhang.utils.SharedPrefsStrListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by LianTu on 2016-10-8.
 */

public class SlideViewPagerAdapter extends FragmentStatePagerAdapter
{

    private List<String> mTitles = new ArrayList<>();
    private final int MAX_TAB_SIZE = 9;

    public SlideViewPagerAdapter(FragmentManager fm,String[] strings) {
        super(fm);
        Collections.addAll(mTitles,strings);
    }

    public void addTag(String title){
        mTitles.add(0,title);
        if (mTitles.size()>MAX_TAB_SIZE){
            mTitles = mTitles.subList(0,MAX_TAB_SIZE);
            mTitles.add("");
            SharedPrefsStrListUtil.putStrListValue(App.getContext(),"Titles",mTitles);
        }
        notifyDataSetChanged();
    }

    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public Fragment getItem(int position) {
       return SearchResultFragment.getInstance(mTitles.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles.get(position).equals("")){
            return "随便逛逛";
        }else {
            return mTitles.get(position);
        }
    }


}
