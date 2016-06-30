package com.ouyang.demo.app.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ToastUtil;

public class FingerPrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        initFingerPrinter();
    }

    private void initFingerPrinter() {
        if (Build.VERSION.SDK_INT >= 23) {
            FingerprintManager manager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            int permission = checkSelfPermission(Manifest.permission.USE_FINGERPRINT);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //没有权限
                if (shouldShowRequestPermissionRationale(Manifest.permission.USE_FINGERPRINT)) {
                    //需要说明为什么要使用该权限
                    ToastUtil.shortToast("说明原因");
                } else {
                    requestPermissions(new String[]{Manifest.permission.USE_FINGERPRINT}, 0);
                }
            }
            //判断指纹识别是否可用
            boolean detected = manager.isHardwareDetected();

            if (!manager.isHardwareDetected()) {
                //指纹识别不可用
                ToastUtil.shortToast("指纹识别不可用");
            } else if (!manager.hasEnrolledFingerprints()) {
                //机器没有录入指纹
                ToastUtil.shortToast("尚未录入指纹");
            } else {
                //可以使用指纹识别
//                manager.authenticate();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ToastUtil.shortToast("permission granted");
//                    callPhone();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ToastUtil.shortToast("permission denied");
                }
                break;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
