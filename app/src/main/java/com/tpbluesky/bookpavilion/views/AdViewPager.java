package com.tpbluesky.bookpavilion.views;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.application.Config;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpbluesky on 2017/2/18.
 */

public class AdViewPager extends FrameLayout {

    private List<ImageView> datas = new ArrayList<ImageView>();
    private List<ImageView> indicators = new ArrayList<ImageView>();

    private LinearLayout llIndicator;

    private FixedViewPager viewPager;

    private AdViewPageAdapter pageAdapter;

    private OnItemClickListener mOnItemClickListener;

    private Handler handler = new Handler();

    private int curItem = -1;

    private AdViewPagerOnPagerChangeListener onPagerChangeListener;

    public AdViewPager(Context context) {
        super(context);
        initView();
    }

    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_ad_viewpager, null);
        this.addView(view);
        viewPager = (FixedViewPager) view.findViewById(R.id.vp_ad);
        llIndicator = (LinearLayout) view.findViewById(R.id.ll_indicator);
        onPagerChangeListener = new AdViewPagerOnPagerChangeListener();
        viewPager.setOnPageChangeListener(onPagerChangeListener);
        pageAdapter = new AdViewPageAdapter(datas);
        viewPager.setAdapter(pageAdapter);
    }

    public void setNestParent(ViewGroup parent) {
        if (viewPager != null) {
            viewPager.setNestParent(parent);
        }
    }

    private void setup() {
        llIndicator.removeAllViews();
        for (int i = 0; i < datas.size(); ++i) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(R.drawable.shape_circle_binner_unfocus);
            iv.setPadding(3, 0, 3, 0);
            indicators.add(iv);
            llIndicator.addView(iv);
        }
        viewPager.setCurrentItem(datas.size() * 10000);
    }

    private void beginCircle() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        }, 5000);
    }

    private void stopCircle() {
        handler.removeCallbacksAndMessages(null);
    }

    public void setImageByUrl(List<String> urlDatas) {
        if (datas.size() != 0) {
            stopCircle();
            datas.clear();
            indicators.clear();
        }
        if (urlDatas != null) {
            for (int i = 0; i < urlDatas.size(); ++i) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                x.image().bind(imageView, Config.SERVER_ACTIVITY + urlDatas.get(i));
                datas.add(imageView);
            }
        }
        pageAdapter.setIvList(datas);
        setup();
    }


    public void setImageByRes(Integer[] resDatas) {
        if (datas.size() != 0) {
            stopCircle();
            datas.clear();
            indicators.clear();
        }
        if (resDatas.length != 0) {
            for (int i = 0; i < resDatas.length; ++i) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(resDatas[i]);
                datas.add(imageView);
            }
        }
        pageAdapter.setIvList(datas);
        setup();
    }

    public void setmOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        for (int i = 0; i < datas.size(); ++i) {
            final int finalI = i;
            datas.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(finalI);
                }
            });
        }
    }

    private void changeSelect(int position) {
        if (indicators.size() == 0) {
            return;
        }
        int pos = position % indicators.size();
        for (int i = 0; i < indicators.size(); ++i) {
            if (i == pos) {
                indicators.get(i).setImageResource(R.drawable.shape_circle_binner_focus);
            } else {
                indicators.get(i).setImageResource(R.drawable.shape_circle_binner_unfocus);
            }
        }
    }

    class AdViewPageAdapter extends PagerAdapter {

        private List<ImageView> ivList;

        public AdViewPageAdapter(List<ImageView> ivDatas) {
            this.ivList = new ArrayList<ImageView>();
            this.ivList.addAll(ivDatas);
        }

        public void setIvList(List<ImageView> ivList) {
            this.ivList.clear();
            this.ivList.addAll(ivList);
            notifyDataSetChanged();
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
            if (ivList.size() == 0) {
                return null;
            }
            ImageView iv = ivList.get(position % ivList.size());
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

    /**
     * AdViewPager页面改变监听器
     * 当用户开始手动滑动时，onPageScrollStateChanged()方法调用，状态为SCROLL_STATE_DRAGGING，
     * 此处移除handler上的所有runable，用户滑完时调用onPageSelected()，此处为handler重新添加延迟执行。
     * 有一点需要注意的是，当用户滑到一半又滑会原来页面时，onPageSelected()是不会调用，此时可以判断当前页和之前的
     * 页面是否相同，如果相同的话，也需要为handler重新添加延迟执行
     */
    class AdViewPagerOnPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            changeSelect(position);
            beginCircle();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    stopCircle();
                    curItem = viewPager.getCurrentItem();
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    if (curItem == viewPager.getCurrentItem()) {
                        beginCircle();
                    }
                    curItem = -1;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
