package com.tpbluesky.bookpavilion.tools;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by tpbluesky on 2017/2/9.
 */

public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return 如果有外部存储，则返回外部存储缓存路径，否则返回内存储缓存路径
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

}
