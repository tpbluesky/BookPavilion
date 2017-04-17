package com.tpbluesky.bookpavilion.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * Created by tpbluesky on 2017/2/14.
 */

public class AppUtils {

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }


}
