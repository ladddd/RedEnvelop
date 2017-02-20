package com.ladddd.redenveloprain;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by ladddd on 2017/2/20.
 */

public class Utils {

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}
