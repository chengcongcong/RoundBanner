package com.cc.bannerlib.utils;

import android.content.Context;

/**
 * Created on 2019-11-04  17:17
 * Description:
 *
 * @author 644898042@qq.com
 */
public class SizeUtil {
    public static int dpToPx(Context context, int i) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (i * scale + 0.5f);
    }
}
