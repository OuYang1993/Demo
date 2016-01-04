package com.ouyang.demo.app.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by oylz on 2015/6/2.
 */
public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();

    /**
     * @param bitmap
     * @return xilinch
     * 将bitmap encode转为string
     * 2014-10-13
     */
    public static String getEncodeBitmap(Bitmap bitmap, int quality) {
        String photo = "";
        if (bitmap != null) {

            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                out.flush();
                out.close();
                byte[] buffer = out.toByteArray();

                byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
                photo = new String(encode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return photo;
    }


    /**
     * 点击EditText 以外的其他地方 隐藏 软键盘
     * 建议在setContentView之后调用
     *
     * @param activity  activity
     * @param container 根布局(所要监听的区域)
     */
    public static void autoHideKeyboard(final Activity activity, final View container) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(container instanceof EditText)) {

            container.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    container.requestFocus();
                    container.setFocusableInTouchMode(true);
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }


        //If a layout container, iterate over children and seed recursion.
        if (container instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) container).getChildCount(); i++) {

                View innerView = ((ViewGroup) container).getChildAt(i);
                autoHideKeyboard(activity, innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View focus = activity.getCurrentFocus();
            if (focus != null) {
                inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置虚拟按钮为半透明状态
     *
     * @param activity activity
     * @param colorId  颜色id
     */
    public static void setNavigationBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(colorId);
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setNavigationBarTintEnabled(true);
            manager.setNavigationBarTintColor(colorId);
        }
    }



    /**
     * 给状态栏设置颜色
     * 提示:如果出现布局上移了,请确认你的activity 对应的xml根布局是否有配置 fillSystemWindow=true
     *
     * @param activity activity
     * @param colorId  颜色id,请用 getResource.getColor(xxx) 传入
     */
    public static void setStatusBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorId);
        }

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager manager = new SystemBarTintManager(activity);
            manager.setStatusBarTintEnabled(true);
            manager.setStatusBarTintColor(colorId);
        }
    }

    /**
     * @return 是否符合规范
     * @Description 当前输入的邮箱地址是否符合规范
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 密码必须是数字和字母的组合，且必须是6-20位
     *
     * @param string
     * @return 是否符合要求
     */
    public static boolean isnumber_letter(String string) {
        String str = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(string);
        return m.matches();
    }
}
