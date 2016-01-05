package com.ouyang.databinding.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by OuYang on 2016/1/5.
 */
public class StringUtil {
    private static String TAG = StringUtil.class.getSimpleName();

    /**
     * 将null字符串转换成空字符串
     *
     * @param str str字符串
     * @return 如果为null则返回空字符串, 否则返回原值
     */
    public static String formatNullString(@Nullable String str) {
        return str == null ? "" : str;
    }

    /**
     * 将数字转换为字符串,如果值为0 则返回空字符串
     *
     * @param num num
     * @return 如果为null则返回空字符串, 否则返回原值
     */
    public static String formatZeroString(int num) {
        return num == 0 ? "" : String.valueOf(num);
    }

    /**
     * 检测字符串是否只包含数字
     *
     * @param str str
     * @return 如果为null则返回空字符串, 否则返回原值
     */
    public static boolean isDigitsOnly(String str) {
        if (str == null)
            return false;
        return TextUtils.isDigitsOnly(str);
    }
}
