package com.tpbluesky.bookpavilion.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by tpbluesky on 2017/2/9.
 */

public class LocalApplication extends Application {

    private static LocalApplication instance;

    //单例模式获取唯一的LocalApplication实例
    public static LocalApplication getInstance() {
        if (instance == null) {
            instance = new LocalApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Xutils3
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        instance = this;

    }
}
