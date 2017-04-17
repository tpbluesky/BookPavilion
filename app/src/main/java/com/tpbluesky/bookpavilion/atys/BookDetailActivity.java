package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.application.Config;
import com.tpbluesky.bookpavilion.http.HttpUtils;
import com.tpbluesky.bookpavilion.http.JsonResult;
import com.tpbluesky.bookpavilion.model.Book;
import com.tpbluesky.bookpavilion.tools.StringUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于显示书籍详情的Activity
 */
@ContentView(R.layout.activity_book_detail)
public class BookDetailActivity extends BaseActivity {

    private static final String TAG = "BookDetailActivity";

    private static final String KEY_TYPE = "type";
    public static final String TYPE_ID = "id";
    public static final String TYPE_ISBN = "isbn";
    private static final String KEY_DATA = "data";
    private static final String KEY_ONSALE = "onSale";

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.iv_share)
    private ImageView iv_share;
    @ViewInject(R.id.iv_book_image)
    private ImageView iv_book_image;
    @ViewInject(R.id.tv_book_name)
    private TextView tv_book_name;
    @ViewInject(R.id.tv_book_price)
    private TextView tv_book_price;
    @ViewInject(R.id.tv_book_left_stock)
    private TextView tv_book_left_stock;
    @ViewInject(R.id.tv_book_origin_price)
    private TextView tv_book_origin_price;
    @ViewInject(R.id.tv_book_sale_out)
    private TextView tv_book_sale_out;
    @ViewInject(R.id.tv_may_for)
    private TextView tv_may_for;
    @ViewInject(R.id.tv_book_author)
    private TextView tv_book_author;
    @ViewInject(R.id.tv_book_publish)
    private TextView tv_book_publish;
    @ViewInject(R.id.tv_book_publish_date)
    private TextView tv_book_publish_date;
    @ViewInject(R.id.tv_book_isbn)
    private TextView tv_book_isbn;
    @ViewInject(R.id.tv_book_total_page)
    private TextView tv_book_total_page;
    @ViewInject(R.id.tv_book_introduce)
    private TextView tv_book_introduce;
    @ViewInject(R.id.ll_detail_collect)
    private LinearLayout ll_detail_collect;
    @ViewInject(R.id.ll_detail_cart)
    private LinearLayout ll_detail_cart;
    @ViewInject(R.id.ll_add_cart)
    private LinearLayout ll_add_cart;

    private Book b;

    private boolean onSale = false;

    @Override
    protected void initViews() {
        onSale = getIntent().getBooleanExtra(KEY_ONSALE, false);
        if (onSale) {
            loadOnSaleBook();
        } else {
            loadNormalBook();
        }

    }

    private void loadNormalBook() {
        Map<String, String> params = new HashMap<String, String>();
        Intent intent = getIntent();
        String type = intent.getStringExtra(KEY_TYPE);
        String data = intent.getStringExtra(KEY_DATA);
        if (type == TYPE_ID) {
            params.put("service", "Book.getBookById");
            params.put("id", data);
        } else if (type == TYPE_ISBN) {
            params.put("service", "Book.getBookByISBN");
            params.put("isbn", data);
        }
        HttpUtils.httpPost(this,params, new HttpUtils.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                JsonResult<List<Book>> jsonResult = StringUtils.String2Gson(result, new TypeToken<List<Book>>() {
                }.getType());
                if(jsonResult.getCode() == 0){

                }else{

                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }


    private void loadOnSaleBook() {
    }


    private void setBookResult() {
        x.image().bind(iv_book_image, Config.SERVER_IMAGE + b.image_src);
        tv_book_name.setText(b.name);
        tv_book_price.setText(b.price_sell);
        tv_book_left_stock.setText(b.stock_left);
        tv_book_origin_price.setText(b.price_origin);
        tv_book_sale_out.setText(b.sale_num);
        tv_book_author.setText(b.author);
        tv_book_publish.setText(b.publish);
        tv_book_publish_date.setText(b.publish_date);
        tv_book_isbn.setText(b.isbn);
        tv_book_total_page.setText(b.page_num);
        tv_book_introduce.setText(b.introduce);
    }

    @Event({R.id.iv_back, R.id.iv_share, R.id.iv_book_image, R.id.ll_detail_collect, R.id.ll_detail_cart, R.id.ll_add_cart})
    private void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                break;
            case R.id.iv_book_image:
                PhotoActivity.startActivity(BookDetailActivity.this, b.image_src);
                break;
            case R.id.ll_detail_collect:

                break;
            case R.id.ll_detail_cart:

                break;
            case R.id.ll_add_cart:

                break;
        }
    }


    public static void startActivity(Context context, String type, String data, boolean onSale) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(KEY_TYPE, type);
        intent.putExtra(KEY_DATA, data);
        intent.putExtra(KEY_ONSALE, onSale);
        context.startActivity(intent);
    }

}
