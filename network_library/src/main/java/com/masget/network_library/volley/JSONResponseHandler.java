package com.masget.network_library.volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by OuYang on 2015/12/25.
 */
public abstract class JSONResponseHandler {

    public abstract void onSuccess(JSONObject object);

    public abstract void onFail(VolleyError error);

//    public abstract void onCache(JSONObject object);
}
