package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.application.Config;
import com.tpbluesky.bookpavilion.model.Book;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by tpbluesky on 2017/2/13.
 */

public class RecommendGridViewAdapter extends AbstractBaseAdapter<Book> {

    private LayoutInflater inflater;

    public RecommendGridViewAdapter(Context context, List<Book> datas) {
        super(datas);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell_book_brief_info, parent, false);
            viewHolder = new BookViewHolder();
            x.view().inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BookViewHolder) convertView.getTag();
        }
        Book b = datas.get(position);
        x.image().bind(viewHolder.iv_book_image, Config.SERVER_IMAGE + b.image_src);
        viewHolder.tv_book_name.setText(b.name);
        viewHolder.tv_book_price.setText(b.price_sell);
        return convertView;
    }

    class BookViewHolder {
        @ViewInject(R.id.iv_book_image)
        ImageView iv_book_image;
        @ViewInject(R.id.tv_book_name)
        TextView tv_book_name;
        @ViewInject(R.id.tv_book_price)
        TextView tv_book_price;
    }
}
