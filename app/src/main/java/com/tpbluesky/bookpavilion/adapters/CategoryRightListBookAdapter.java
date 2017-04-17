package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.model.Book;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by tpbluesky on 2017/2/25.
 */

/**
 * Category右边ListView的适配器
 */
public class CategoryRightListBookAdapter extends AbstractBaseAdapter<Book> {

    private Context mContext;
    private LayoutInflater mInflater;

    public CategoryRightListBookAdapter(Context context, List<Book> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryRightViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cell_category_right_list_item, parent, false);
            holder = new CategoryRightViewHolder();
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryRightViewHolder) convertView.getTag();
        }
        //TODO
        //此处写绑定的逻辑
        return convertView;
    }

    class CategoryRightViewHolder {

        @ViewInject(R.id.iv_book_image)
        private ImageView iv_book_image;
        @ViewInject(R.id.tv_book_name)
        private TextView tv_book_name;
        @ViewInject(R.id.tv_book_price)
        private TextView tv_book_price;
        @ViewInject(R.id.tv_book_left_stock)
        private TextView tv_book_left_stock;
    }
}