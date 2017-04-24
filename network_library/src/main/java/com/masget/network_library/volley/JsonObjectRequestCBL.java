package com.masget.network_library.volley;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by OuYang on 2015/12/25.
 *自定义JsonObject请求
 *
 */
public class JsonObjectRequestCBL extends JsonObjectRequest {
    public JsonObjectRequestCBL(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public JsonObjectRequestCBL(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public JsonObjectRequestCBL(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public JsonObjectRequestCBL(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public JsonObjectRequestCBL(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }
}
