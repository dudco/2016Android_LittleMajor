package com.example.dudco.and_sojoun_pro;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dudco on 2016. 12. 29..
 */

public class Util {

    public static float Px2DP(Context context, float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    public static boolean canDrawOverlays(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }else{
            return Settings.canDrawOverlays(context);
        }
    }
    public static HashMap<String, Integer> getDivSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        HashMap<String, Integer> data = new HashMap<>();
        data.put("width", width);
        data.put("height", height);

        return data;
    }

}
