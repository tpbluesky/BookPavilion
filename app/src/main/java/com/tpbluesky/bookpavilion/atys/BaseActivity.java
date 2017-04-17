package com.tpbluesky.bookpavilion.atys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by tpbluesky on 2017/2/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private AlertDialog netWorkErrorDialog = null;

    private ProgressDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用Xutils注解绑定控件
        x.view().inject(this);
        initDialogs();
        initViews();
    }

    protected void initDialogs() {
        netWorkErrorDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("连接错误，请检查网络或者请求配置是否正确")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("加载中...");
        loadingDialog.setCancelable(false);
    }

    /**
     * 初始化布局内容
     */
    protected abstract void initViews();

    protected void showNetWorkErrorDialog() {
        netWorkErrorDialog.show();
    }

    protected void showProgressDialog() {
        loadingDialog.show();
    }

    private void hideProgressDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
