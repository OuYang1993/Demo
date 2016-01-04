package com.ouyang.demo.app.utils;

import android.widget.Toast;
import com.ouyang.demo.app.base.BaseApp;


public final class ToastUtil {

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    public static void shortToast(Object text) {
        showToast(text, LENGTH_SHORT);
    }

    public static void longToast(Object text) {
        showToast(text, LENGTH_LONG);
    }

    public static void showToast(Object text, int duration) {
        createToast(text, duration).show();
    }

    public static Toast shortToast(Toast toast, Object text) {
        return showToast(toast, text, LENGTH_SHORT);
    }

    public static Toast longToast(Toast toast, Object text) {
        return showToast(toast, text, LENGTH_LONG);
    }

    public static Toast showToast(Toast toast, Object text, int duration) {
        if (toast == null) {
            toast = createToast(text, duration);
        } else {
            toast.setText(String.valueOf(text));
        }
        toast.show();
        return toast;
    }

    private static Toast toast = null;

    public static Toast createToast(Object text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(BaseApp.getInstance(), String.valueOf(text), duration);
        } else {
            toast.setText(String.valueOf(text));
            toast.setDuration(duration);
        }
        return toast;
    }

}
