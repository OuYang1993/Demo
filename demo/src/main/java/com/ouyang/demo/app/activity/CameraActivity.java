package com.ouyang.demo.app.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.config.BoxingCropOption;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.databinding.ActivityCameraBinding;
import com.ouyang.demo.app.utils.LogUtil;
import com.ouyang.demo.app.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCameraBinding binding;
    private int REQUEST_CODE_PICK = 1011;
    private static final int REQUEST_TAKE_PHOTO = 1012;
    private Uri photoURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        setupActionBar();
        setListener();
    }

    private void setListener() {
        binding.btnSelectPhoto.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("图片");
        }
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择图片");
        CharSequence[] items = new CharSequence[]{"从相册选择", "拍照"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int c = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA);
                int r = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                int w = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int i = c + r + w;
                switch (which) {
                    case 0:
                        if (i == 0) {
                            pickPicture();
                        } else {
                            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                        break;

                    case 1:
                        if (i == 0) {
                            takePhoto();
                        } else {
                            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                        break;
                }
            }
        });
        builder.show();
    }

    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ToastUtil.shortToast("文件创建失败");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, getPackageName() + ".file.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void pickPicture() {
        BoxingConfig singleImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG);
        Boxing.of(singleImgConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_CODE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PICK) {
                List<BaseMedia> medias = Boxing.getResult(data);
                if (medias != null && medias.size() > 0) {
                    String path = medias.get(0).getPath();
                    binding.sdvPick.setImageURI(Uri.parse("file://" + path));
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                LogUtil.e("take photo path:    " + photoURI.getPath());
                binding.sdvPick.setImageURI(photoURI);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickPicture();
            } else {
                ToastUtil.shortToast("请赋予应用相机权限");
            }
        }
    }
}
