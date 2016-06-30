package com.ouyang.demo.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ouyang.demo.app.utils.LogUtil;

/**
 * Created by OuYang on 2016/6/30.
 */
public class NetworkReceiver extends BroadcastReceiver {
    private final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            LogUtil.e(TAG, "network changed: " + typeName);
            if (listener != null)
                listener.onNetworkChanged(true, networkInfo);
        } else {
            LogUtil.e(TAG, "network is null or network is not available");
            if (listener != null)
                listener.onNetworkChanged(false, networkInfo);
        }
    }

    private OnNetworkStateChangedListener listener;

    public void setOnNetworkChangedListener(OnNetworkStateChangedListener listener) {
        this.listener = listener;
    }

    interface OnNetworkStateChangedListener {
        void onNetworkChanged(boolean canConnect, NetworkInfo networkInfo);
    }
}
