package com.tpbluesky.bookpavilion.fragments;

import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


/**
 * 用于处理购物车的Fragment
 */
@ContentView(R.layout.fragment_cart)
public class CartFragment extends BaseFragment {


    @ViewInject(R.id.cb_check_all)
    private CheckBox cb_check_all;
    @ViewInject(R.id.tv_tatol_money)
    private TextView tv_tatol_money;
    @ViewInject(R.id.tv_go_to_pay)
    private TextView tv_go_to_pay;

    @ViewInject(R.id.lv_cart_book_list)
    private ListView lv_cart_book_list;


    @Override
    protected void initViews() {

    }
}
