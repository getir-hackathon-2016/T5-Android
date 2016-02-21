package com.getir.getirandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.getir.getirandroid.fragments.TutorialPageFragment;

public class TutorialPagerAdapter extends FragmentStatePagerAdapter {

    public TutorialPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TutorialPageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
