package com.tpbluesky.bookpavilion.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by tpbluesky on 2017/2/20.
 */

public class FixedViewPager extends ViewPager {

    private ViewGroup parent;

    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNestParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (parent != null)
                    parent.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_CANCEL:
                if (parent != null)
                    parent.requestDisallowInterceptTouchEvent(false);
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}

