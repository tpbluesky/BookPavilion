package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.BookListAdapter;
import com.tpbluesky.bookpavilion.model.Book;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示搜索结果或者榜单结果的Activity
 */
@ContentView(R.layout.activity_book_list)
public class BookListActivity extends BaseActivity {

    private static final String KEY_SEARCH_STRING = "search_string";

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.ll_search)
    private LinearLayout ll_search;
    @ViewInject(R.id.tv_search_text)
    private TextView tv_search_text;
    @ViewInject(R.id.lv_book_list)
    private ListView lv_book_list;

    private BookListAdapter adapter;
    private List<Book> bookBeanDatas = new ArrayList<Book>();

    @Override
    protected void initViews() {
        String text = getIntent().getStringExtra(KEY_SEARCH_STRING);
        tv_search_text.setText(text);
        loadBookDatas();
        adapter = new BookListAdapter(this, bookBeanDatas);
        lv_book_list.setAdapter(adapter);
    }

    private void loadBookDatas() {
        for (int i = 0; i < 12; ++i) {
            bookBeanDatas.add(new Book());
        }
    }

    public static void startActivity(Context context, String searchString) {
        Intent intent = new Intent(context, BookListActivity.class);
        intent.putExtra(KEY_SEARCH_STRING, searchString);
        context.startActivity(intent);
    }

    @Event({R.id.iv_back, R.id.ll_search})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_search:
                finish();
                break;
        }
    }

}
