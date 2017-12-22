package com.loction.choose.flowchooselibrary.util;

import android.content.Context;

/**
 * Created by localadmin on 2017/12/22.
 */

public class DimmenUtils {

    /**
     * dp 转 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    public static int dp2px(Context context,final float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    public static int px2dp(Context context,final float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }



    /**
     * sp 转 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    public static int sp2px(Context context,final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    public static int px2sp(Context context,final float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
