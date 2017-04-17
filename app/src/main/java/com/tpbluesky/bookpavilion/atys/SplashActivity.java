package com.tpbluesky.bookpavilion.atys;

import android.content.Intent;
import android.os.Handler;
import android.view.WindowManager;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;

/**
 * 打开APP时显示的欢迎界面
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();

    @Override
    protected void initViews() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 500);
    }

}
