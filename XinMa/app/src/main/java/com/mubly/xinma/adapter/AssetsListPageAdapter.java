package com.mubly.xinma.adapter;

import com.mubly.xinma.fragment.AssetsListFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AssetsListPageAdapter extends FragmentPagerAdapter {
    private List<String> titleList;

    public AssetsListPageAdapter(FragmentManager fm, List<String> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {

        return AssetsListFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
