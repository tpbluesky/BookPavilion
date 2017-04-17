package com.tpbluesky.bookpavilion.atys;

import android.content.Context;
import android.content.Intent;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.application.Config;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import uk.co.senab.photoview.PhotoView;


@ContentView(R.layout.activity_photo)
public class PhotoActivity extends BaseActivity {

    private static final String KEY_DATA = "url";

    @ViewInject(R.id.photo_view)
    private PhotoView photo_view;

    @Override
    protected void initViews() {
        String url = getIntent().getStringExtra(KEY_DATA);
        if (url != null) {
            x.image().bind(photo_view, Config.SERVER_IMAGE+url);
        }
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(KEY_DATA, url);
        context.startActivity(intent);
    }
}
