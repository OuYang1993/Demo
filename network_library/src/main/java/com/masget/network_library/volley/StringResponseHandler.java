package com.masget.network_library.volley;

import com.android.volley.VolleyError;

/**
 * Created by OuYang on 2015/12/25.
 */
public abstract class StringResponseHandler {

    public abstract void onSuccess(String  object);

    public abstract void onFail(VolleyError error);

//    public abstract void onCache(JSONObject object);
}
