package com.tpbluesky.bookpavilion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tpbluesky.bookpavilion.R;
import com.tpbluesky.bookpavilion.model.Category;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by tpbluesky on 2017/3/7.
 */

public class CategoryLeftListBookAdapter extends AbstractBaseAdapter<Category> {

    private Context context;
    private LayoutInflater inflater;

    private int colorWhite, colorSelect;

    private int lastPosition = 0;


    public CategoryLeftListBookAdapter(Context context, List<Category> datas) {
        super(datas);
        this.context = context;
        inflater = LayoutInflater.from(context);
        colorWhite = context.getResources().getColor(R.color.app_theme_color_light);
        colorSelect = context.getResources().getColor(R.color.app_theme_selected_color);
    }

    public void setLastPosition(int lastPosition) {
        if (lastPosition != this.lastPosition) {
            this.lastPosition = lastPosition;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.cell_category_left_list_item, parent, false);
        CategoryLeftViewHolder holder = new CategoryLeftListBookAdapter.CategoryLeftViewHolder();
        x.view().inject(holder, convertView);
        holder.tv_category_name.setText(datas.get(position).getName());
        if (lastPosition == position) {
            holder.ll_category_left_item.setBackgroundColor(colorWhite);
            holder.tv_indicator_color.setBackgroundColor(colorSelect);
            holder.tv_category_name.setTextColor(colorSelect);
        }
        return convertView;
    }

    class CategoryLeftViewHolder {

        @ViewInject(R.id.ll_category_left_item)
        private LinearLayout ll_category_left_item;
        @ViewInject(R.id.tv_indicator_color)
        private TextView tv_indicator_color;
        @ViewInject(R.id.tv_category_name)
        private TextView tv_category_name;

    }
}