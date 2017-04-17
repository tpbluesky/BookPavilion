package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.views.ToastMaker;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by tpbluesky on 2017/2/13.
 */

/**
 * Index页面五个榜GridView数据适配器
 */
public class RankGridViewAdapter extends AbstractBaseAdapter {

    private int[] names = {R.string.text_index_rank_one, R.string.text_index_rank_two, R.string.text_index_rank_three,
            R.string.text_index_rank_four, R.string.text_index_rank_five};
    private int[] imgs = {R.mipmap.rank_first, R.mipmap.rank_second, R.mipmap.rank_third, R.mipmap.rank_fourth, R.mipmap.rank_fifth};

    private LayoutInflater inflater;
    private Context context;

    public RankGridViewAdapter(Context context) {
        super(null);
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        RankViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new RankViewHolder();
            convertView = inflater.inflate(R.layout.cell_index_rank_item, parent,false);
            x.view().inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastMaker.makeShortToast(context.getResources().getString(names[position]) + position);
                }
            });
        } else {
            viewHolder = (RankViewHolder) convertView.getTag();
        }
        viewHolder.iv_book_image.setImageResource(imgs[position]);
        viewHolder.tv_book_price.setText(context.getResources().getString(names[position]));
        return convertView;
    }

    class RankViewHolder {
        @ViewInject(R.id.iv_rank)
        ImageView iv_book_image;
        @ViewInject(R.id.tv_rank)
        TextView tv_book_price;
    }
}
