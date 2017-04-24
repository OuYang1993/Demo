package com.masget.network_library.volley;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.masget.network_library.BaseApplication;
import com.masget.network_library.bean.Constants;
import com.masget.network_library.util.EncryptUtil;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by OuYang on 2015/12/25.
 * volley网络请求工具类
 */
public class VolleyRequest {
    private final static String BASE_URL = "https://192.168.87.72:7373/openapi/rest/";
    private static final String TAG = VolleyRequest.class.getSimpleName();
    private static final int TIME_OUT = 15 * 1000;
    private static final String HOST = BASE_URL;

    /**
     * 发起 JsonObjectRequest post请求
     *
     * @param encrypt    是否加密
     * @param methodName 方法名(不需要ip地址)
     * @param params     参数(不加密)
     * @param handler    回调
     */
    public static void postJson(boolean encrypt, final String methodName, final Map<String, String> params, final JSONResponseHandler handler) {

        JSONObject object;
        if (encrypt) {
            Map<String, String> encrypted = encryptParams(params);
            object = new JSONObject(encrypted);
        } else {
            object = new JSONObject(params);
        }
        HttpsTrustManager.allowAllSSL();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + methodName, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onFail(error);
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Accept-Language", Locale.getDefault().toString());
                map.put("Host", HOST);
                map.put("Connection", "Keep-Alive");
                return map;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BaseApplication.getInstance().addToRequestQueue(request, methodName);
    }

    /**
     * 发起 JsonObjectRequest post请求,若碰到base url不同的情况可用此方法单独配置baseUrl
     *
     * @param baseUrl    可选接口地址
     * @param methodName 方法名(不需要ip地址)
     * @param params     参数(不加密)
     * @param handler    回调
     */
    public static void postJson(final String baseUrl, final String methodName, Map<String, String> params, final JSONResponseHandler handler) {

        Map<String, String> encrypted = encryptParams(params);
        JSONObject object = new JSONObject(encrypted);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + methodName, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onFail(error);
                    }
                }) {
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BaseApplication.getInstance().addToRequestQueue(request, methodName);

    }

    /**
     * 发起 JsonObjectRequest get请求
     * 如果要取消该请求,使用methodName 作为参数
     *
     * @param methodName 方法名(不需要ip地址)
     * @param params     参数(不加密)
     * @param handler    回调
     */
    public static void getJson(final String methodName, Map<String, String> params, final JSONResponseHandler handler) {

        String url = initGetUrl(methodName, params);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handler.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handler.onFail(error);
                    }
                }) {
        };

        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        BaseApplication.getInstance().addToRequestQueue(request, methodName);

    }


    /**
     * 拼接get请求的 Url,post请求勿用
     *
     * @param methodName 方法名
     * @param params     参数
     * @return 拼接完成的get url
     */
    private static String initGetUrl(String methodName, Map<String, String> params) {
        Map<String, String> encrypted = encryptParams(params);
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        try {
            Iterator<Map.Entry<String, String>> iterator = encrypted.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            return BASE_URL + methodName + sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将原有参数进行加密后转换成最终服务器所需要的数据
     *
     * @param params 原有参数
     * @return 加密后的参数
     */
    private static Map<String, String> encryptParams(Map<String, String> params) {
        JSONObject object = new JSONObject(params);

        String data = EncryptUtil.aesEncrypt(object.toString(), Constants.AES_KEY, Constants.AES_KEY);
        String sign = EncryptUtil.string2MD5(data + Constants.AES_KEY);

        Map<String, String> map = new HashMap<>();
        map.put("data", data);
        map.put("sign", sign);

        return map;
    }

    /**
     * 对参数进行加密,如果加密,则进行aes加密,否则只进行base64加密
     *
     * @param isAesEncrypt 是否加密
     * @param data         data
     * @param key          key
     * @return encrypted result
     */
    public String getEncrtResult(boolean isAesEncrypt, String data, String key) {
        String encryption = "";
        if (!isAesEncrypt) {
            Base64 base64 = new Base64();
            try {
                encryption = new String(base64.encode(data.getBytes("utf-8")), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                encryption = EncryptUtil.aesEncrypt(data, key, key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encryption;
    }


}
