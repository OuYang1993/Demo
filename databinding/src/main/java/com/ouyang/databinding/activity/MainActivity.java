package com.ouyang.databinding.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ouyang.databinding.R;
import com.ouyang.databinding.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_normal;
    private Button btn_async1;
    private Button btn_list;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        checkPermission();
        logPath();
    }

    private void logPath() {
        Log.e(TAG, "Environment.getDataDirectory :  " + Environment.getDataDirectory());
        Log.e(TAG, "Environment.getExternalStorageState : " + Environment.getExternalStorageState());
        Log.e(TAG, "Environment.getDownloadCacheDirectory:  " + Environment.getDownloadCacheDirectory());
        Log.e(TAG, "Environment.getExternalStorageDirectory: " + Environment.getExternalStorageDirectory());
        Log.e(TAG, "Environment.getRootDirectory:" + Environment.getRootDirectory());
        Log.e(TAG, "getFilesDir: " + getFilesDir());
        Log.e(TAG, "getPackageResourcePath: " + getPackageResourcePath());
        Log.e(TAG, "getApplicationInfo().dataDir: " + getApplicationInfo().dataDir);
    }

    /**
     * 检测是否拥有 crashHandler 弹框所必须的权限
     */
    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Test permission request dialog");
                builder.setTitle("tips");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                    }
                });
                builder.setNegativeButton("cancel", null);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void callPhone() {

        //调用系统拨打电话
        Intent phoneIntent = new Intent();
        phoneIntent.setAction(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:" + "18520074003"));
        startActivity(phoneIntent);
    }


    private void initView() {
        btn_normal = (Button) findViewById(R.id.btn_normal);
        btn_async1 = (Button) findViewById(R.id.btn_async1);
        btn_list = (Button) findViewById(R.id.btn_list);
    }

    private void setListener() {
        btn_normal.setOnClickListener(this);
        btn_async1.setOnClickListener(this);
        btn_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_normal:
                intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
//                callPhone();
                break;
            case R.id.btn_async1:
                intent = new Intent(MainActivity.this, SyncOneActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_list:
                intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
