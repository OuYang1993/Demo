package com.ouyang.code_scan.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.connectek.CDecodeResultListener;
import com.connectek.CHSMDecoder;
import com.honeywell.barcode.HSMDecodeResult;
import com.ouyang.code_scan.R;
import com.ouyang.code_scan.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity implements View.OnClickListener, CDecodeResultListener {

    private ActivityMainBinding binding;
    private CHSMDecoder decoder;
    private final int REQUEST_PERMISSION_WRITE_EXTERNAL = 1;
    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initScanner();
        setListener();
    }

    private void initScanner() {
        boolean hasPermission = checkPermissions(REQUEST_PERMISSION_WRITE_EXTERNAL, Manifest.permission.CAMERA);
        if (hasPermission) {
            decoder = CHSMDecoder.getInstance(this);
            decoder.enableUPCA();
            decoder.enableCODE128();
            decoder.enableCODE39();
            decoder.enableEAN13();
            decoder.enableQR();
            decoder.enableFlashOnDecode(false);
            decoder.enableAimer(true);
            decoder.addResultListener(this);
        } else {
            Toast.makeText(this, "没有相机权限", Toast.LENGTH_SHORT).show();
        }

    }

    private void setListener() {
        binding.btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                scan();
                break;
        }
    }

    private void scan() {
        if (decoder == null) {
            initScanner();
        } else {
            decoder.scanBarcode();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop  ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy  ");
        CHSMDecoder.disposeInstance();
    }

    @Override
    public void onHSMDecodeResult(HSMDecodeResult[] hsmDecodeResults) {
        displayBarcodeData(hsmDecodeResults);
    }

    private void displayBarcodeData(HSMDecodeResult[] barcodeData) {
        try {
            if (barcodeData.length > 0) {
                HSMDecodeResult firstResult = barcodeData[0];
                String data = firstResult.getBarcodeData();
                if (!TextUtils.isEmpty(data)) {
                    binding.tvResult.setText("Result: " + new String(data.getBytes(), "UTF-8"));
                }
                binding.tvSymb.setText("Symbology: " + firstResult.getSymbology());
                binding.tvLength.setText("Length: " + firstResult.getBarcodeDataLength());
                binding.tvDecodeTime.setText("Decode Time: " + firstResult.getDecodeTime() + "ms");
                binding.ivDecode.setImageBitmap(decoder.getLastImage());// getLastBarcodeImage(firstResult.getBarcodeBounds()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult " + grantResults[0]);
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE_EXTERNAL:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //权限已赋予
                    initScanner();
                }
                break;
        }
    }
}
