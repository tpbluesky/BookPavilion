package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;

import com.tpbluesky.bookpavilion.R;

import org.xutils.view.annotation.ContentView;

/**
 * 用于显示消息的Activity
 */
@ContentView(R.layout.activity_message)
public class MessageActivity extends BaseActivity {

    @Override
    protected void initViews() {

    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MessageActivity.class));
    }
}
