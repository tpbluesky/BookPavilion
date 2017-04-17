package com.tpbluesky.bookpavilion.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by tpbluesky on 2017/2/9.
 */

public abstract class BaseFragment extends Fragment {

    private AlertDialog netWorkErrorDialog = null;

    private ProgressDialog loadingDialog = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        initDialogs();
        initViews();
        return view;
    }

    /**
     * 初始化所有View的数据
     */
    protected abstract void initViews();

    protected void initDialogs() {
        netWorkErrorDialog = new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("连接错误，请检查网络或者请求配置是否正确")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage("加载中...");
        loadingDialog.setCancelable(false);
    }

    protected void showNetWorkErrorDialog() {
        netWorkErrorDialog.show();
    }

    protected void showProgressDialog() {
        loadingDialog.show();
    }

    protected void hideProgressDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
