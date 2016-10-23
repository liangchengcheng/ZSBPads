package com.lcc.utils;

import android.content.Context;
import android.graphics.Typeface;
import com.github.johnpersano.supertoasts.SuperToast;
import zsbpj.lccpj.frame.FrameManager;

public class CoCoinUtil {

    public static SuperToast.Animations TOAST_ANIMATION = SuperToast.Animations.FLYIN;

    public static Typeface typefaceLatoRegular = null;
    public static Typeface typefaceLatoHairline = null;
    public static Typeface typefaceLatoLight = null;

    public static void init(Context context) {
        typefaceLatoRegular = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Regular.ttf");
        typefaceLatoHairline = Typeface.createFromAsset(
                context.getAssets(), "fonts/Lato-Hairline.ttf");
        typefaceLatoLight = Typeface.createFromAsset(
                context.getAssets(), "fonts/LatoLatin-Light.ttf");
    }

    private static CoCoinUtil ourInstance = new CoCoinUtil();

    public static CoCoinUtil getInstance() {
        if (ourInstance == null || typefaceLatoLight == null || typefaceLatoHairline == null) {
            ourInstance = new CoCoinUtil();
            init(FrameManager.getAppContext());
        }
        return ourInstance;
    }

    private CoCoinUtil() {
    }
}
