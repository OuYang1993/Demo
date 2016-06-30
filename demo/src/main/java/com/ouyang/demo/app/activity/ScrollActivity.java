package com.ouyang.demo.app.activity;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ScrollActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private Toolbar toolbar;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initView();
        setupActionBar();
        setAdapter();
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("双向滑动列表");

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAdapter() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 21; i++) {
            list.add("item " + i);
        }
        adapter = new RecyclerAdapter(this, list);
        rv_list.setAdapter(adapter);

        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//只允许上下拖动
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragFlag, 0) {//此处参数 会被 getMovementFlag 方法 覆盖
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.onItemDismiss(position, direction);//此处的direction 与 getMovementFlags 设置的值相对应

            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//                int swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//可以上下左右拖动
                return makeMovementFlags(dragFlag, 0);//只需要排序, 不需要滑动删除
            }
        });

        helper.attachToRecyclerView(rv_list);
    }

    private void initView() {
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scroll, menu);
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
}
