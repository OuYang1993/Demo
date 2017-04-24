package com.ouyang.demo.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private final String TAG = DetailActivity.class.getSimpleName();
    private TextView tv_content;
    private Button btn_swipe;
    private Button btn_call;
    private String packageName = "com.masget.mgchat.mpos";
    private String clzName = "com.masget.mgchat.mpos.pay.PayDispatcherActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BaseTheme_Yellow);
        setContentView(R.layout.activity_detail);
        getWindow().setEnterTransition(new Explode());
        initView();
        setListener();
    }

    private void initView() {
        btn_swipe = (Button) findViewById(R.id.btn_swipe);
        btn_call = (Button) findViewById(R.id.btn_call);
        tv_content = (TextView) findViewById(R.id.tv_search_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);//导航图标
//        toolbar.setNavigationIcon();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setListener() {
        btn_swipe.setOnClickListener(this);
        btn_call.setOnClickListener(this);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:

                        break;
                }

                return true;
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        Intent.createChooser(intent, "share");
        provider.setShareIntent(intent);


        //searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.i(TAG, "onQueryTextSubmit  " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG, "onQueryTextChange  " + newText);
                tv_content.setText(newText);
                return false;
            }
        });
        return true;
    }




    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_swipe:
                intent = new Intent(DetailActivity.this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_call:
//                intent = new Intent(DetailActivity.this, ViewPagerActivity.class);
//                startActivity(intent);
                getDaysBetween("2015-12-31", "2017-01-01");
                break;
        }
    }

    public static int getDaysBetween(String starDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date fromDate = format.parse(starDate);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(fromDate);

            Date toDate = format.parse(endDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(toDate);

            long startTimeInMillis = startCalendar.getTimeInMillis();
            long endTimeInMillis = endCalendar.getTimeInMillis();
            long between =  endTimeInMillis - startTimeInMillis;
            int days = (int) (between / (24 * 60 * 60 * 1000));
            LogUtil.e("日期相差: " + days);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            //消费成功
            Bundle extras = data.getExtras();
            Log.e("detail", "onActivityResult " + extras.getString("result"));
        }
    }
}
