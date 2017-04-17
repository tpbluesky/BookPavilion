package com.tpbluesky.bookpavilion.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by tpbluesky on 2017/2/20.
 */

public class FixedPtrFrameLayout extends PtrFrameLayout {

    private boolean disallowInterceptTouchEvent = false;

    public FixedPtrFrameLayout(Context context) {
        super(context);
    }

    public FixedPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallowInterceptTouchEvent = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:

                this.requestDisallowInterceptTouchEvent(false);
                break;
        }
        if (disallowInterceptTouchEvent) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }
}
