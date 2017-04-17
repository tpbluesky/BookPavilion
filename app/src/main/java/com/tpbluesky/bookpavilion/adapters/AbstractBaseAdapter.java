package com.tpbluesky.bookpavilion.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpbluesky on 2017/2/13.
 */

/**
 * ListView的Adapter抽象类
 *
 * @param <T> 要显示数据的类型
 */
public abstract class AbstractBaseAdapter<T> extends BaseAdapter {


    protected List<T> datas = new ArrayList<T>();


    public AbstractBaseAdapter(List<T> datas) {
        if (datas != null) {
            this.datas = datas;
        }
    }

    public void setDatas(List<T> datas) {
        if (datas != null) {
            this.datas = datas;
            notifyDataSetChanged();
        }
    }

    public void addDatas(List<T> datas) {
        if (datas != null) {
            this.datas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
