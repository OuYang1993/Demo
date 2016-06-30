package com.ouyang.demo.app.activity;

import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.fragment.FragmentOne;
import com.ouyang.demo.app.fragment.FragmentThree;
import com.ouyang.demo.app.fragment.FragmentTwo;


public class TabActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentTabHost tabHost;
    private Button btn_1, btn_2, btn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        initView();
        setListener();
    }

    private void setListener() {
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
    }

    private void initView() {

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);

        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.real_tab_content);

        tabHost.addTab(tabHost.newTabSpec("one").setIndicator("消息"), FragmentOne.class, null);
        tabHost.addTab(tabHost.newTabSpec("two").setIndicator("主页"), FragmentTwo.class, null);
        tabHost.addTab(tabHost.newTabSpec("three").setIndicator("我"), FragmentThree.class, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                tabHost.setCurrentTabByTag("one");
                break;
            case R.id.btn_2:
                tabHost.setCurrentTabByTag("two");
                break;
            case R.id.btn_3:
                tabHost.setCurrentTabByTag("three");
                break;
        }
    }
}
