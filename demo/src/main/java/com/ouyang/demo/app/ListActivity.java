package com.ouyang.demo.app;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.ouyang.demo.app.adapter.ListAdapter;
import com.ouyang.demo.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {

    private ListView lv_list;
    private Toolbar toolbar;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        initView();
        setupActionBar();
        initData();
        setAdapter();
        setListener();
    }

    private void setListener() {
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, SupportDesignActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add("ripple test item " + (i + 1));
        }
    }

    private void setAdapter() {
        ListAdapter adapter = new ListAdapter(this, list);
        lv_list.setAdapter(adapter);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lv_list = (ListView) findViewById(R.id.lv_list);
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ListView ripple 测试");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
