package com.lcc.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    {  }
 */
public class SpManager {

    public static void setUN(Context context,String unStr){
        SharedPreferences sp=context.getSharedPreferences("lcc", Context.MODE_APPEND);
        sp.edit().putString("un", unStr).apply();
    }

    public static String getUN(Context context){
        SharedPreferences sp=context.getSharedPreferences("lcc", Context.MODE_APPEND);
        return sp.getString("un","");
    }
}
