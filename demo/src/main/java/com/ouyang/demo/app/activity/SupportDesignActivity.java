package com.ouyang.demo.app.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.RecyclerAdapter1;

import java.util.ArrayList;
import java.util.List;


public class SupportDesignActivity extends AppCompatActivity {

    private RecyclerView rv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_design);

        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        setAdapter();

    }

    private void setAdapter() {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("support design item " + (i + 1));
        }
        RecyclerAdapter1 adapter = new RecyclerAdapter1(this, list);
        rv_list.setAdapter(adapter);
    }

}
