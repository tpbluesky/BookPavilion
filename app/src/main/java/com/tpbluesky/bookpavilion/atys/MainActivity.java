package com.tpbluesky.bookpavilion.atys;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.MainViewPagerAdapter;
import com.tpbluesky.bookpavilion.fragments.CartFragment;
import com.tpbluesky.bookpavilion.fragments.CategoryFragment;
import com.tpbluesky.bookpavilion.fragments.IndexFragment;
import com.tpbluesky.bookpavilion.fragments.UserCenterFragment;
import com.tpbluesky.bookpavilion.views.ToastMaker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 主Activity
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    //首页
    @ViewInject(R.id.ll_index)
    private LinearLayout ll_index;
    @ViewInject(R.id.iv_index)
    private ImageView iv_index;
    @ViewInject(R.id.tv_index)
    private TextView tv_index;
    //分类
    @ViewInject(R.id.ll_category)
    private LinearLayout ll_category;
    @ViewInject(R.id.iv_category)
    private ImageView iv_category;
    @ViewInject(R.id.tv_category)
    private TextView tv_category;
    //购物车
    @ViewInject(R.id.ll_cart)
    private LinearLayout ll_cart;
    @ViewInject(R.id.iv_cart)
    private ImageView iv_cart;
    @ViewInject(R.id.tv_cart)
    private TextView tv_cart;
    //我的
    @ViewInject(R.id.ll_usercenter)
    private LinearLayout ll_usercenter;
    @ViewInject(R.id.iv_usercenter)
    private ImageView iv_usercenter;
    @ViewInject(R.id.tv_usercenter)
    private TextView tv_usercenter;

    //四个页面的Fragment
    private IndexFragment mIndexFragment;
    private CategoryFragment mCategoryFragment;
    private CartFragment mCartFragment;
    private UserCenterFragment mUserCenterFragment;

    //用于四个Fragment进行切换的ViewPager
    @ViewInject(R.id.vp_main)
    private ViewPager vp_main;
    private MainViewPagerAdapter mViewPagerMainAdapter;

    //上一次点击返回键的时间
    private long mFirstClick = 0;

    @Override
    protected void initViews() {
        List<Fragment> fList = new ArrayList<Fragment>();
        mIndexFragment = new IndexFragment();
        fList.add(mIndexFragment);
        mCategoryFragment = new CategoryFragment();
        fList.add(mCategoryFragment);
        mCartFragment = new CartFragment();
        fList.add(mCartFragment);
        mUserCenterFragment = new UserCenterFragment();
        fList.add(mUserCenterFragment);
        mViewPagerMainAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), fList);
        vp_main.setAdapter(mViewPagerMainAdapter);
        vp_main.setCurrentItem(0);
        showIndicator(0);
        vp_main.setOffscreenPageLimit(4);
    }

    public void setCurrentFragmennt(int position) {
        vp_main.setCurrentItem(position, true);
        showIndicator(position);
    }

    /**
     * 修改指示器状态
     *
     * @param position 要显示的是第几页
     */
    private void showIndicator(int position) {
        resetAll();
        switch (position) {
            case 0:
                tv_index.setSelected(true);
                iv_index.setSelected(true);
                break;
            case 1:
                tv_category.setSelected(true);
                iv_category.setSelected(true);
                break;
            case 2:
                tv_cart.setSelected(true);
                iv_cart.setSelected(true);
                break;
            case 3:
                iv_usercenter.setSelected(true);
                tv_usercenter.setSelected(true);
                break;
        }
    }

    /**
     * 恢复所有的指示器为初始状态
     */
    private void resetAll() {
        iv_index.setSelected(false);
        iv_category.setSelected(false);
        iv_cart.setSelected(false);
        iv_usercenter.setSelected(false);
        tv_index.setSelected(false);
        tv_category.setSelected(false);
        tv_cart.setSelected(false);
        tv_usercenter.setSelected(false);
    }

    /**
     * 底部导航栏四个控件的单击事件
     */
    @Event({R.id.ll_index, R.id.ll_category, R.id.ll_cart, R.id.ll_usercenter})
    private void onViewClick(View v) {
        int operator = -1;
        switch (v.getId()) {
            case R.id.ll_index:
                operator = 0;
                break;
            case R.id.ll_category:
                operator = 1;
                break;
            case R.id.ll_cart:
                operator = 2;
                break;
            case R.id.ll_usercenter:
                operator = 3;
                break;
        }
        if (operator != -1) {
            vp_main.setCurrentItem(operator, false);
            showIndicator(operator);
        }
    }

    /**
     * 当用户在一秒内点击两次返回键才退出程序，否则不退出程序
     */
    @Override
    public void onBackPressed() {
        long secondClick = System.currentTimeMillis();
        if (secondClick - mFirstClick > 1000) {
            ToastMaker.makeShortToast("再按一次返回键退出旧书阁");
            mFirstClick = secondClick;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ViewPage的某一个选中事件
     */
    @Event(type = ViewPager.OnPageChangeListener.class, value = R.id.vp_main, method = "onPageSelected")
    private void onViewPageSelected(int arg0) {
        showIndicator(arg0);
    }
}
