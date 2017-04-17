package com.tpbluesky.bookpavilion.http;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created by tpbluesky on 2017/3/10.
 */

public class CartBookStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        String name = f.getName();
        if (name.equals("id") || name.equals("name") || name.equals("image_src")
                || name.equals("price_sell") || name.equals("stock_left")
                || name.equals("order_num"))
            return false;
        return true;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
