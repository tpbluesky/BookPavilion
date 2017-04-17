package com.tpbluesky.bookpavilion.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * IndexFragment内AdViewPager的数据适配器
 */
public class AdViewPagerAdapter extends PagerAdapter {

    private List<ImageView> ivs;

    public AdViewPagerAdapter(List<ImageView> ivs) {
        if (ivs == null) {
            this.ivs = new ArrayList<ImageView>();
            return;
        }
        this.ivs = ivs;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int curPosition = position % ivs.size();
        ImageView iv = ivs.get(curPosition);
        ViewParent vp = iv.getParent();
        if (vp != null) {
            ((ViewGroup) vp).removeView(iv);
        }
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
