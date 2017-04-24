package com.masget.network_library;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.masget.network_library.bean.User;
import com.masget.network_library.util.EncryptUtil;

/**
 * 使用volley的项目需要继承该类并使用
 */
public class BaseApplication extends Application {

    private static final String KEY_MOBILE = "user.mobile";
    private static final String KEY_STAFF_ID = "user.staffid";
    private static final String KEY_USERNAME = "user.userName";
    private static final String KEY_APP_KEY = "user.appKey";
    private static final String KEY_PASSWORD = "user.password";
    private static final String KEY_SESSION = "user.session";

    public static BaseApplication instance;
    private RequestQueue requestQueue;
    private SharedPreferences userPref;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        userPref = getSharedPreferences("user_info", MODE_PRIVATE);
    }


    /**
     * 保存用户信息到 shared preference
     *
     * @param user user
     */
    public void saveUserInfo(User user) {
        SharedPreferences.Editor editor = userPref.edit();
        editor.putString(KEY_USERNAME, user.getLoginname());
        editor.putString(KEY_APP_KEY, user.getAppkey());
        editor.putString(KEY_PASSWORD, EncryptUtil.aesEncrypt(user.getPassword(), user.getAppkey(), user.getAppkey()));
        editor.putString(KEY_SESSION, user.getSession());
        editor.putString(KEY_STAFF_ID, user.getStaffid());
        editor.putString(KEY_MOBILE, user.getMobilephone());
        editor.apply();
    }



    /**
     * 从shared preference获取用户信息
     *
     * @return user
     */
    public User getUserInfo() {
        User user = new User();
        user.setAppkey(userPref.getString(KEY_APP_KEY, ""));
        user.setLoginname(userPref.getString(KEY_USERNAME, ""));
        user.setMobilephone(userPref.getString(KEY_MOBILE, ""));
        String password = EncryptUtil.aesDecrypt(userPref.getString(KEY_PASSWORD, ""), userPref.getString(KEY_APP_KEY, ""), userPref.getString(KEY_APP_KEY, ""));
        user.setPassword(password);
        user.setSession(userPref.getString(KEY_SESSION, ""));
        user.setStaffid(userPref.getString(KEY_STAFF_ID, ""));
        return user;
    }

    /**
     * 获取请求队列
     *
     * @return requestQueue
     */
    //volley 相关  start
    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * 将请求添加到队列执行
     *
     * @param request request 请求
     * @param tag     此tag用来取消该请求
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

}
