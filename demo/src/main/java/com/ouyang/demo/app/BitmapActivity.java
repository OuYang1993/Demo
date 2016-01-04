package com.ouyang.demo.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ouyang.demo.app.adapter.RecyclerAdapter;
import com.ouyang.demo.app.adapter.RecyclerAdapter1;

import java.util.ArrayList;
import java.util.List;

/**
 * 布局生成bitmap测试
 */
public class BitmapActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_content;
    private LinearLayout layout_content;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        initView();
        setAdapter();
        setListener();

    }

    private void setListener() {
        btn1.setOnClickListener(this);
    }

    private void setAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_content.setLayoutManager(manager);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("bitmap item " + (i + 1));
        }
        RecyclerAdapter1 adapter = new RecyclerAdapter1(this, list);
        rv_content.setAdapter(adapter);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("bitmap");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        layout_content = (LinearLayout) findViewById(R.id.layout_content);
        btn1 = (Button) findViewById(R.id.btn_1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                layout_content.setAnimationCacheEnabled(true);
                layout_content.buildDrawingCache();
                showBitmapDialog();
                break;
        }
    }

    private void showBitmapDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("bitmap");
        dialog.show();
        Window window = dialog.getWindow();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_bitmap, null, false);
        ImageView iv_bitmap = (ImageView) view.findViewById(R.id.iv_bitmap);
        Bitmap drawingCache = getBitmapFromView(layout_content);
        iv_bitmap.setImageBitmap(drawingCache);
        window.setContentView(view);
    }

    public static Bitmap getBitmapFromView(View view) {

        int width = view.getWidth();
        int height = view.getHeight();

        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        //Cause the view to re-layout
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap returnedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
}
