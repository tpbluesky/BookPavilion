package com.tpbluesky.bookpavilion.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity内ViewPager适配器
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter{

	//要显示的Fragment列表
	List<Fragment> fragments;
	
	public MainViewPagerAdapter(FragmentManager fm, List<Fragment> fList) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.addAll(fList);
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

}
