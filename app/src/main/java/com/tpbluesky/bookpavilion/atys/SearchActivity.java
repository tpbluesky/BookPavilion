package com.tpbluesky.bookpavilion.atys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.adapters.AbstractBaseAdapter;
import com.tpbluesky.bookpavilion.database.DBUtils;
import com.tpbluesky.bookpavilion.database.SQLiteHelper;
import com.tpbluesky.bookpavilion.tools.DispalyUtils;
import com.tpbluesky.bookpavilion.views.FlowLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于进行搜索的Activity
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";

    private SQLiteDatabase dw;

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.et_search)
    private EditText et_search;
    @ViewInject(R.id.iv_clear)
    private ImageView iv_clear;
    @ViewInject(R.id.tv_search)
    private TextView tv_search;
    @ViewInject(R.id.ll_history)
    private LinearLayout ll_history;
    @ViewInject(R.id.iv_delete_all)
    private ImageView iv_delete_all;
    @ViewInject(R.id.gv_hot_search)
    private GridView gv_hot_search;
    @ViewInject(R.id.flow_layout)
    private FlowLayout flow_layout;

    List<String> datas = new ArrayList<String>();


    @Override
    protected void initViews() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                iv_clear.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        loadHotSearchDatas();
        gv_hot_search.setAdapter(new HotSearchGridViewAdapter(this, datas));
        List<String> searchStringDatas = DBUtils.getSearchString();
        for (int i = 0; i < searchStringDatas.size(); ++i) {
            addTextView(searchStringDatas.get(i));
        }
    }

    private void loadHotSearchDatas() {
        for (int i = 1; i <= 12; ++i) {
            datas.add("高等数学" + i);
        }

        //TODO
        //加载热搜历史
    }

    @Override
    protected void onResume() {
        super.onResume();
        et_search.setText("");
    }

    //将搜索的内容加到搜索历史里面
    private void addTextView(String text) {
        if (ll_history.getVisibility() == View.INVISIBLE) {
            ll_history.setVisibility(View.VISIBLE);
        }
        TextView textView = (TextView) flow_layout.findViewWithTag(text);
        if (text != null) {
            flow_layout.removeView(textView);
        }
        TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.cell_hot_search_item, null);
        tv.setText(text);
        tv.setTag(text);
        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mlp.rightMargin = DispalyUtils.dip2px(this, 5);
        mlp.topMargin = DispalyUtils.dip2px(this, 5);
        tv.setLayoutParams(mlp);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginSearch(((TextView) v).getText().toString());
            }
        });
        flow_layout.addView(tv, 0);
    }

    //开始搜索
    private void beginSearch(String text) {
        BookListActivity.startActivity(this, text);
        addTextView(text);
        DBUtils.cacheSearchString(text);
    }

    /**
     * 一些控件的单击事件
     *
     * @param v
     */
    @Event({R.id.iv_back, R.id.iv_clear, R.id.tv_search, R.id.iv_delete_all})
    private void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                et_search.setText("");
                break;
            case R.id.tv_search:
                String text = et_search.getText().toString();
                if (text.equals("")) {
                    text = et_search.getHint().toString();
                    BookListActivity.startActivity(SearchActivity.this, text);
                } else {
                    beginSearch(text);
                }
                break;
            case R.id.iv_delete_all:
                ll_history.setVisibility(View.INVISIBLE);
                flow_layout.removeAllViews();
                DBUtils.clearSearchString();
                break;
        }
    }


    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }


    /**
     * 搜索历史GridView的Adapter
     */
    private class HotSearchGridViewAdapter extends AbstractBaseAdapter<String> {

        private Context context;
        private LayoutInflater inflater;

        public HotSearchGridViewAdapter(Context context, List<String> datas) {
            super(datas);
            inflater = LayoutInflater.from(context);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.cell_hot_search_item, null);
                viewHolder = new ViewHolder();
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
                viewHolder.tvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BookListActivity.startActivity(context, ((TextView) v).getText().toString());
                    }
                });
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvText.setText(datas.get(position));
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.tv_text)
            private TextView tvText;
        }
    }
}
