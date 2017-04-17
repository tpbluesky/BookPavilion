package com.tpbluesky.bookpavilion.views;

import android.app.ProgressDialog;

import com.tpbluesky.bookpavilion.application.LocalApplication;

/**
 * Created by tpbluesky on 2017/2/10.
 */

public class DialogMaker {

    public static void showProgressDialog(String msg){
        ProgressDialog ps = new ProgressDialog(LocalApplication.getInstance(),ProgressDialog.STYLE_SPINNER);
        ps.show(LocalApplication.getInstance(),"",msg,false,false);
    }
}
