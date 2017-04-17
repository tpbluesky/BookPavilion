package com.tpbluesky.bookpavilion.tools;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by tpbluesky on 2017/2/13.
 */

public class DispalyUtils {

    private DispalyUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dip转化为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转化为dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px转化为sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static float px2sp(Context context, float pxValue) {
        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * sp转化为px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, int spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }


}
