package com.lcc.utils;

import android.graphics.Color;

import com.github.johnpersano.supertoasts.SuperToast;

import zsbpj.lccpj.frame.FrameManager;

public class CoCoinToast {
    private static CoCoinToast ourInstance = new CoCoinToast();
    public static CoCoinToast getInstance() {
        return ourInstance;
    }

    private CoCoinToast() {
    }

    public void showToast(int text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(FrameManager.getAppContext());
        superToast.setAnimations(CoCoinUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(FrameManager.getAppContext().getResources().getString(text));
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(CoCoinUtil.typefaceLatoLight);
        superToast.show();
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(FrameManager.getAppContext());
        superToast.setAnimations(CoCoinUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.getTextView().setTypeface(CoCoinUtil.typefaceLatoLight);
        superToast.show();
    }
}
