package com.ouyang.code_scan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.ouyang.code_scan.R;

/**
 * Created by OuYang on 2016/8/2.
 */
public class BaseActivity extends AppCompatActivity {

    private AlertDialog.Builder builder;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }


    /**
     * 判断权限集合
     *
     * @param permissions
     * @return
     */
    private boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     *
     * @param permission
     * @return true:缺少;false:已申请权限
     */
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * 请求权限兼容低版本
     */
    public void requestPermissions(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 显示缺失权限提示
     */
    public void showMissingPermissionDialog(final int code, final String... permissions) {
        if (builder == null)
            builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);

        builder.setNegativeButton(R.string.quit, null);

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                startAppSettings();
                requestPermissions(code, permissions);
            }
        });

        builder.show();
    }

    /**
     * 启动应用的设置
     */
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 用户关闭并不再提醒所有权限提
     *
     * @param permissions
     * @return
     */
    private boolean hasDelayAllPermissions(String... permissions) {
        int count = 0;
        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                    && ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                count++;
            }
        }
        if (count == permissions.length) {
            return true;
        }
        return false;
    }

    /**
     * 检查权限
     *
     * @param permissions
     * @param code        请求码, 用来区分同时请求多个权限时的每个权限的结果
     */
    public boolean checkPermissions(int code, @NonNull String... permissions) {
        if (lacksPermissions(permissions)) {
            if (!hasDelayAllPermissions(permissions)) {
                showMissingPermissionDialog(code, permissions);
            } else {
                requestPermissions(code, permissions);
            }
            return false;
        } else {
            return true;
        }
    }

}
