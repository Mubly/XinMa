package com.mubly.xinma.utils;

/**
 * Created by Leon on 16/9/23.
 */
public class ClickUtil {
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
