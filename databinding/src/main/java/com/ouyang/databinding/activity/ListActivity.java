package com.ouyang.databinding.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.ouyang.databinding.R;
import com.ouyang.databinding.adapter.UserAdapter;
import com.ouyang.databinding.databinding.ActivityListBinding;
import com.ouyang.databinding.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView rv_list;
    private SwipeRefreshLayout srf_refresh;
    private List<User> list;
    private UserAdapter adapter;
    private MyHandler handler = new MyHandler();
    private LinearLayoutManager manager;
    private ActivityListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        setupActionBar();
        initView();
        setListener();
        setAdapter();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        srf_refresh = (SwipeRefreshLayout) findViewById(R.id.srf_refresh);
        srf_refresh.setColorSchemeResources(R.color.colorPrimary);
    }

    private void setListener() {
        binding.etListContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (list != null && list.size() > 0) {
                    list.get(0).setName(binding.etListContent.getText().toString());
                }
            }
        });

        srf_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addNewUser();
            }
        });


        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.onItemDismiss(position, direction);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;

                    Paint paint = new Paint();
                    Bitmap bitmap;

                    if (dX > 0) { // swiping right
                        paint.setColor(getResources().getColor(R.color.material_teal_500));
                        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_done_white_24dp);
                        float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);

                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);
                        c.drawBitmap(bitmap, 81f, (float) itemView.getTop() + height, null);

                    } else { // swiping left
                        paint.setColor(getResources().getColor(R.color.material_yellow_500));
                        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_delete_white_24dp);
                        float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);
                        float bitmapWidth = bitmap.getWidth();

                        c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
                        c.drawBitmap(bitmap, ((float) itemView.getRight() - bitmapWidth) - 81f, (float) itemView.getTop() + height, null);
                    }

                    float width = (float) viewHolder.itemView.getWidth();
                    float alpha = 1.0f - Math.abs(dX) / width;
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
        });
        helper.attachToRecyclerView(rv_list);
    }

    private void addNewUser() {
        User user = new User();
        user.setName("new 姓名" + list.size());
        user.setSex("new 性别" + "男");
        user.setAddress("new 地址" + list.size());
        user.setAge(list.size());
        list.add(0, user);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setAdapter() {
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(manager);

        list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            User user = new User();
            user.setName("名字 " + i);
            user.setAge(10 + i);
            user.setSex(i % 2 == 0 ? "男" : "女");
            user.setAddress("地址: " + i + " 号");
            list.add(user);
        }

        adapter = new UserAdapter(this, list);
        rv_list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    srf_refresh.setRefreshing(false);
//                    adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(0);
                    manager.scrollToPosition(0);
                    break;
            }
        }
    }
}
