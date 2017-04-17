package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 用于显示Ad详情的Activity
 */
@ContentView(R.layout.activity_addetail)
public class AdDetailActivity extends BaseActivity {

    @ViewInject(R.id.iv_back)
    private ImageView iv_back;
    @ViewInject(R.id.web_view)
    private WebView web_view;

    @Override
    protected void initViews() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String url = getIntent().getStringExtra("url");
//        WebSettings settings = web_view.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        Log.e("asdasd", "initViews: "+url );
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, AdDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
