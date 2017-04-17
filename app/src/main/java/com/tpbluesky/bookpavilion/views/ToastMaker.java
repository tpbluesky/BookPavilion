package com.tpbluesky.bookpavilion.views;

import android.widget.Toast;

import com.tpbluesky.bookpavilion.application.LocalApplication;

/**
 * Created by tpbluesky on 2017/2/10.
 */

public class ToastMaker {

    public static void makeShortToast(String msg) {
        Toast.makeText(LocalApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }

    public static void makeLongToast(String msg) {
        Toast.makeText(LocalApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
}
