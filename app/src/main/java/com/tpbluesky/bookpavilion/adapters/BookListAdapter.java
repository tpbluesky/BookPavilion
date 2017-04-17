package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.graphics.Paint;
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
 * Created by tpbluesky on 2017/2/23.
 */

/**
 * 书籍列表ListView的Adapter
 */
public class BookListAdapter extends AbstractBaseAdapter<Book> {

    private Context mContext;
    private LayoutInflater inflater;

    public BookListAdapter(Context context, List<Book> datas) {
        super(datas);
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cell_search_result_book_list_item, null);
            holder = new ViewHolder();
            x.view().inject(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_book_origin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.iv_book_image)
        ImageView iv_book_image;
        @ViewInject(R.id.tv_book_name)
        TextView tv_book_name;
        @ViewInject(R.id.tv_book_author)
        TextView tv_book_author;
        @ViewInject(R.id.tv_book_origin_price)
        TextView tv_book_origin_price;
        @ViewInject(R.id.tv_book_sale_out)
        TextView tv_book_sale_out;
    }
}
