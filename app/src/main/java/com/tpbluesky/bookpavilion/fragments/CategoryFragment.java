package com.tpbluesky.bookpavilion.fragments;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.mylhyl.zxing.scanner.common.Intents;
import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.CategoryLeftListBookAdapter;
import com.tpbluesky.bookpavilion.adapters.CategoryRightListBookAdapter;
import com.tpbluesky.bookpavilion.atys.ScannerActivity;
import com.tpbluesky.bookpavilion.atys.SearchActivity;
import com.tpbluesky.bookpavilion.http.HttpUtils;
import com.tpbluesky.bookpavilion.http.JsonResult;
import com.tpbluesky.bookpavilion.model.Book;
import com.tpbluesky.bookpavilion.model.Category;
import com.tpbluesky.bookpavilion.tools.StringUtils;
import com.tpbluesky.bookpavilion.views.ToastMaker;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于呈现书籍分类的Fragment
 */
@ContentView(R.layout.fragment_category)
public class CategoryFragment extends BaseFragment {

    private static final String TAG = "CategoryFragment";

    //左边的ListView
    @ViewInject(R.id.lv_category_left)
    private ListView lv_category_left;
    private CategoryLeftListBookAdapter mCategoryLeftListBookAdapter;
    private List<Category> categoryDatas = new ArrayList<Category>();

    //右边的ListView
    @ViewInject(R.id.lv_category_right)
    private ListView lv_category_right;
    private CategoryRightListBookAdapter mCategoryRightListBookAdapter;
    private List<Book> bookList = new ArrayList<Book>();

    //Toolbar上面的搜索和扫一扫
    @ViewInject(R.id.ll_category_search)
    private LinearLayout ll_category_search;
    @ViewInject(R.id.ll_category_scan)
    private LinearLayout ll_category_scan;

    @Override
    protected void initViews() {
        initCategoryLeft();
        initCategoryRight();
    }

    //初始化Category左边的布局
    private void initCategoryLeft() {
        loadCategoryLeftData();
        mCategoryLeftListBookAdapter = new CategoryLeftListBookAdapter(getActivity(), null);
        lv_category_left.setAdapter(mCategoryLeftListBookAdapter);
        lv_category_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCategoryLeftListBookAdapter.setLastPosition(position);
            }
        });
    }

    //初始化Category右边的布局
    private void initCategoryRight() {
        loadCategoryRightData();
        mCategoryRightListBookAdapter = new CategoryRightListBookAdapter(getActivity(), null);
        lv_category_right.setAdapter(mCategoryRightListBookAdapter);
        mCategoryRightListBookAdapter.setDatas(bookList);
        lv_category_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //此处描写点击事件
            }
        });
    }

    //加载左边的分类数据
    private void loadCategoryLeftData() {
        Map<String, String> params = new HashMap<>();
        params.put("service", "Category.GetCategoryList");
        HttpUtils.httpPost(getActivity(), params, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                JsonResult<List<Category>> jsonResult = StringUtils.String2Gson(result, new TypeToken<JsonResult<List<Category>>>() {
                }.getType());
                if (jsonResult.getCode() == 0) {
                    categoryDatas = jsonResult.getInfo();
                    mCategoryLeftListBookAdapter.setDatas(categoryDatas);
//                    DBUtils.cacheCategoryCache(categoryDatas);
                } else {
                    ToastMaker.makeShortToast(jsonResult.getMsg());
                }
            }

            @Override
            public void onFail(String error) {
                ToastMaker.makeShortToast(error);
            }
        });
    }


    //加载右边的数据，每次选中时都会加载
    private void loadCategoryRightData() {
        for (int i = 0; i < 10; ++i) {
            bookList.add(new Book());
        }
    }

    //单击事件
    @Event({R.id.ll_category_search, R.id.ll_category_scan})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_category_search:
                SearchActivity.startActivity(getActivity());
                break;
            case R.id.ll_category_scan:
                startActivityForResult(new Intent(getActivity(), ScannerActivity.class), ScannerActivity.REQUEST_CODE);
                break;
        }
    }

    //二维码扫码返回的结果接收
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ScannerActivity.REQUEST_CODE:
                    String stringExtra = data.getStringExtra(Intents.Scan.RESULT);
                    ToastMaker.makeShortToast(stringExtra);
                    break;
            }
        }
    }


}
