package com.lcc.utils;

import android.content.Context;
import android.view.View;

public class ResourcesUtils {

    public static int getColor(View view, int resId) {
        return view.getResources().getColor(resId);
    }
    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

}
