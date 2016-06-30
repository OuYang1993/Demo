package com.ouyang.demo.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.DrawerAdapter;
import com.ouyang.demo.app.adapter.RecyclerAdapter;
import com.ouyang.demo.app.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_content;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private RecyclerView rv_drawer;
    private RecyclerAdapter adapter;
    private final String TAG = MainActivity.class.getSimpleName();
    //    private SwipeRefreshLayout srf_refresh;
    private List<String> list = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setExitTransition(new Explode());
        initView();
        setListener();
        setAdapter();
        LogUtil.e(TAG, "xxxxxxxxxxxxxxxxxxxx");

    }

    private void setListener() {

    }

    private void setAdapter() {

        for (int i = 0; i < 20; i++) {
            list.add("this is item " + i);
        }

        for (int i = 0; i < 10; i++) {
            list1.add("drawer item " + i);
        }


        adapter = new RecyclerAdapter(this, list);
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, list1);
        rv_content.setAdapter(adapter);
        rv_drawer.setAdapter(drawerAdapter);
    }

    private void initView() {

//        srf_refresh = (SwipeRefreshLayout) findViewById(R.id.sfr_refresh);
//        srf_refresh.setColorSchemeResources(R.color.material_red_500, R.color.material_green_500, R.color.material_blue_500, R.color.material_yellow_500);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.drawer_open);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle(R.string.drawer_close);
            }

        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        rv_drawer = (RecyclerView) findViewById(R.id.rv_drawer);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager manager1 = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_content.setLayoutManager(manager);
        rv_drawer.setLayoutManager(manager1);
        rv_content.setHasFixedSize(true);
        rv_drawer.setHasFixedSize(true);

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.onItemDismiss(position, direction);//此处的direction 与 getMovementFlags 设置的值相对应

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;

                    Paint paint = new Paint();
                    Bitmap bitmap;

                    if (dX > 0) { // swiping right
                        paint.setColor(getResources().getColor(R.color.material_deep_teal_500));
                        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_done_white_24dp);
                        float height = (itemView.getHeight() / 2) - (bitmap.getHeight() / 2);

                        c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);
                        c.drawBitmap(bitmap, 81f, (float) itemView.getTop() + height, null);

                    } else { // swiping left
                        paint.setColor(getResources().getColor(R.color.material_yellow_500));
                        bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_delete_white_24dp);
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

        helper.attachToRecyclerView(rv_content);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(this, FingerPrintActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_list) {
            Intent intent = new Intent(this, ScrollActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_recycler) {
            Intent intent = new Intent(this, RecyclerViewActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_bitmap) {
            Intent intent = new Intent(this, BitmapActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
