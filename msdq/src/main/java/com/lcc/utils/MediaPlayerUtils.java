package com.lcc.utils;


import java.util.Formatter;
import java.util.Locale;

public class MediaPlayerUtils {

    private static StringBuilder mFormatBuilder;
    private static Formatter mFormatter;

    static {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
    }

    public static String getVideoDisplayTime(long timeMs) {
        int totalSeconds = (int) timeMs / 1000;

        int seconds=totalSeconds%60;
        int minutes=(totalSeconds/60)%60;
        int hours=totalSeconds/3600;

        mFormatBuilder.setLength(0);
        if (hours>0){
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
