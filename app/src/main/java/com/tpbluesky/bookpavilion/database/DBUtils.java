package com.tpbluesky.bookpavilion.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tpbluesky.bookpavilion.application.LocalApplication;
import com.tpbluesky.bookpavilion.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpbluesky on 2017/3/7.
 */

public class DBUtils {

    private static SQLiteDatabase dw = new SQLiteHelper(LocalApplication.getInstance()).getWritableDatabase();


    /**
     * 获取缓存的分类信息
     *
     * @return 分类列表
     */
    public static List<Category> getCategoryCache() {
        List<Category> datas = new ArrayList<Category>();
        Cursor c = dw.query(SQLiteHelper.TB_CATEGORY_CACHE, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.getCount() != 0) {
            do {
                datas.add(new Category(
                        c.getString(c.getColumnIndex(SQLiteHelper.KEY_CATEGORY_ID)),
                        c.getString(c.getColumnIndex(SQLiteHelper.KEY_CATEGORY_NAME))
                ));
            } while (c.moveToNext());
        }
        return datas;
    }

    /**
     * 缓存从网络获取的分类信息
     *
     * @param datas 分类信息
     */
    public static void cacheCategoryCache(List<Category> datas) {
        dw.delete(SQLiteHelper.TB_CATEGORY_CACHE, null, null);
        ContentValues cvs = new ContentValues();
        for (int i = 0; i < datas.size(); ++i) {
            Category bean = datas.get(i);
            cvs.put(SQLiteHelper.KEY_CATEGORY_ID, bean.getId());
            cvs.put(SQLiteHelper.KEY_CATEGORY_NAME, bean.getName());
        }
        dw.insert(SQLiteHelper.TB_CATEGORY_CACHE, null, cvs);
    }


    /**
     * 获取缓存的搜索历史
     *
     * @return 搜索历史列表
     */
    public static List<String> getSearchString() {
        List<String> datas = new ArrayList<>();
        Cursor c = dw.query(SQLiteHelper.TB_SEARCH_CACHE, new String[]{SQLiteHelper.KEY_SEARCH_STRING}, null,
                null, null, null, SQLiteHelper.KEY_SEARCH_TIME + " asc");
        c.moveToFirst();
        if (c.getCount() != 0) {
            do {
                datas.add(c.getString(c.getColumnIndex(SQLiteHelper.KEY_SEARCH_STRING)));
            } while (c.moveToNext());
        }
        return datas;
    }

    /**
     * 缓存搜索历史
     *
     * @param search_string 搜索字符串
     * @return 数据库中是否存在该字符串
     */
    public static boolean cacheSearchString(String search_string) {
        boolean hasString = false;
        Cursor c = dw.query(SQLiteHelper.TB_SEARCH_CACHE, null, SQLiteHelper.KEY_SEARCH_STRING + "=?", new String[]{search_string}, null, null, null);
        if (c.getCount() != 0) {
            dw.delete(SQLiteHelper.TB_SEARCH_CACHE, SQLiteHelper.KEY_SEARCH_STRING + "=?", new String[]{search_string});
            hasString = true;
        }
        ContentValues cv = new ContentValues();
        cv.put(SQLiteHelper.KEY_SEARCH_STRING, search_string);
        dw.insert(SQLiteHelper.TB_SEARCH_CACHE, null, cv);
        return hasString;
    }

    /**
     * 删除所以缓存的历史搜索
     */
    public static void clearSearchString() {
        dw.delete(SQLiteHelper.TB_SEARCH_CACHE, null, null);
    }

}
