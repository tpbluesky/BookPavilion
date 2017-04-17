package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tpbluesky.bookpavilion.model.Book;

import java.util.List;
import java.util.Map;

/**
 * Created by tpbluesky on 2017/3/9.
 */

public class CartBookListAdapter extends AbstractBaseAdapter<Map<Book, Integer>> {

    public CartBookListAdapter(Context context, List<Map<Book, Integer>> datas) {
        super(datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
