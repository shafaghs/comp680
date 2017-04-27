package com.comp680.sunlink.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.comp680.sunlink.fragments.TabFragment1;
import com.comp680.sunlink.fragments.TabFragment2;
import com.comp680.sunlink.fragments.TabFragment3;

class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               return new TabFragment1();
            case 1:
               return new TabFragment2();
            case 2:
                return new TabFragment3();
            default:
               return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}