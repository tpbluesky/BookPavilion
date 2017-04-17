package com.tpbluesky.bookpavilion.http;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by tpbluesky on 2017/3/10.
 */

public class IndexBookStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        String name = f.getName();
        if (name.equals("id") || name.equals("name") || name.equals("image_src") ||
                name.equals("price_sell")) {
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
