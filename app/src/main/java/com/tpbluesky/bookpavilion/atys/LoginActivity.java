package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.MainViewPagerAdapter;
import com.tpbluesky.bookpavilion.fragments.LoginapFragment;
import com.tpbluesky.bookpavilion.fragments.LoginpcFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于登录注册的Activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.rl_left)
    private RelativeLayout rl_left;
    @ViewInject(R.id.rl_right)
    private RelativeLayout rl_right;
    @ViewInject(R.id.iv_left_selected_bar)
    private ImageView iv_left_selected_bar;
    @ViewInject(R.id.iv_right_selected_bar)
    private ImageView iv_right_selected_bar;
    @ViewInject(R.id.login_viewpager)
    private ViewPager login_viewpager;
    @ViewInject(R.id.tv_left)
    private TextView tv_left;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    private MainViewPagerAdapter adapter;
    private LoginapFragment mLoginapFragment;
    private LoginpcFragment mLoginpcFragment;
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void initViews() {
        mLoginapFragment = new LoginapFragment();
        mLoginpcFragment = new LoginpcFragment();
        fragmentList.add(mLoginpcFragment);
        fragmentList.add(mLoginapFragment);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        login_viewpager.setAdapter(adapter);
        login_viewpager.setCurrentItem(0);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }


    @Event(value = R.id.login_viewpager, type = ViewPager.OnPageChangeListener.class, method = "onPageSelected")
    private void onPagerSelected(int position) {
        switch (position) {
            case 0:
                tv_left.setTextColor(getResources().getColor(R.color.app_theme_selected_color));
                tv_right.setTextColor(getResources().getColor(R.color.app_theme_text_color_second));
                iv_left_selected_bar.setVisibility(View.VISIBLE);
                iv_right_selected_bar.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tv_right.setTextColor(getResources().getColor(R.color.app_theme_selected_color));
                tv_left.setTextColor(getResources().getColor(R.color.app_theme_text_color_second));
                iv_right_selected_bar.setVisibility(View.VISIBLE);
                iv_left_selected_bar.setVisibility(View.INVISIBLE);
                break;
        }
    }


    @Event({R.id.iv_back, R.id.rl_left, R.id.rl_right})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_left:
                login_viewpager.setCurrentItem(0);
                break;
            case R.id.rl_right:
                login_viewpager.setCurrentItem(1);
                break;
        }
    }

}
