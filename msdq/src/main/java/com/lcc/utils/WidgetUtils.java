package com.lcc.utils;

import android.view.View;

public class WidgetUtils {

    public static void requestFocus(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }
}
