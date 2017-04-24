package com.ouyang.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bilibili.boxing.model.config.BoxingCropOption;
import com.ouyang.test.databinding.ActivityMainBinding;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private String TAG = MainActivity.class.getSimpleName();
    private int REQ_BOXING_CROP = 102;
    private int REQ_CODE_PICK = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setListener();
    }

    private void setListener() {
        binding.btnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_select:
                startActivityForResult(getPickImageIntent(), REQ_CODE_PICK);
                break;
        }
    }

    private Intent getPickImageIntent() {
        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE_PICK) {
                Uri uri = data.getData();
                binding.sdvContent.setImageURI(Uri.parse(uri.toString()));
                String path = uri.getPath();
                Log.e(TAG, "拍照图片路径: " + path);
                UCrop.Options crop = new UCrop.Options();

                Uri destUri = Uri.fromFile(new File(getExternalCacheDir(), "crop_temp" + System.currentTimeMillis() + ".jpeg"));
                // do not copy exif information to crop pictures
                // because png do not have exif and png is not Distinguishable
                crop.setCompressionFormat(Bitmap.CompressFormat.PNG);
                final BoxingCropOption cropOption = new BoxingCropOption(destUri);
//                cropOption.aspectRatio(1, 1);
                crop.withMaxResultSize(cropOption.getMaxWidth(), cropOption.getMaxHeight());
                crop.withAspectRatio(cropOption.getAspectRatioX(), cropOption.getAspectRatioY());
                UCrop.of(uri, destUri).withOptions(crop).start(this, REQ_BOXING_CROP);

            } else if (requestCode == REQ_BOXING_CROP) {
                handleBoxingCrop(data);
            }
        }
    }

    private void handleBoxingCrop(Intent data) {
        if (data == null) {
            return;
        }
        Throwable throwable = UCrop.getError(data);
        if (throwable != null) {
            return;
        }
        Uri output = UCrop.getOutput(data);
        try {
            String imagePath = output.getPath();
            Log.e(TAG, "裁剪图片地址: " + imagePath);
            binding.sdvContent.setImageURI(Uri.parse("file://" + imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
