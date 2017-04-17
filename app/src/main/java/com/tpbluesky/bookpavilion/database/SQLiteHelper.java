package com.tpbluesky.bookpavilion.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tpbluesky on 2017/2/21.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    //数据库名
    public static final String APP_CACHE = "app_cache";

    //搜索缓存
    public static final String TB_SEARCH_CACHE = "search_cache_table";
    public static final String KEY_SEARCH_STRING = "search_string";
    public static final String KEY_SEARCH_TIME = "search_time";
    private static final String SQL_SEARCH_CACHE = "create table if not exists " + TB_SEARCH_CACHE + "(" +
            "search_string varchar(40) primary key," +
            "search_time timestamp default (datetime('now','localtime')));";

    //分类缓存
    public static final String TB_CATEGORY_CACHE = "category_cache_table";
    public static final String KEY_CATEGORY_ID = "category_id";
    public static final String KEY_CATEGORY_NAME = "category_name";
    public static final String SQL_CATEGORY_CACHE = "create table if not exists " + TB_CATEGORY_CACHE + "(" +
            KEY_CATEGORY_ID + " varchar(5) primary key, " +
            KEY_CATEGORY_NAME + " varchar(30) not null );";

    public SQLiteHelper(Context context) {
        super(context, APP_CACHE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CATEGORY_CACHE);
        Log.e("hhhhhhh", "onCreate: " + SQL_CATEGORY_CACHE);
        db.execSQL(SQL_SEARCH_CACHE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
