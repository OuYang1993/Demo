package com.ouyang.design.app;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.ouyang.design.app.adapter.FragmentAdapter;
import com.ouyang.design.app.adapter.RecyclerAdapter;
import com.ouyang.design.app.fragmet.FragmentFour;
import com.ouyang.design.app.fragmet.FragmentOne;
import com.ouyang.design.app.fragmet.FragmentThree;
import com.ouyang.design.app.fragmet.FragmentTwo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private TabLayout tableLayout;
    private ViewPager vp_main;
    private final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab_action;
    private CollapsingToolbarLayout ctl_toolbar;
    private AppBarLayout app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        initView();
        setAdapter();
        setListener();
    }

    @Override
    public void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        tableLayout = (TabLayout) findViewById(R.id.tab);
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        fab_action = (FloatingActionButton) findViewById(R.id.fab_action);

        ctl_toolbar = (CollapsingToolbarLayout) findViewById(R.id.ctl_toolbar);
        app_bar = (AppBarLayout) findViewById(R.id.appbar);



    }

    private void setupActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT == 21) {
            int result = 0;
            int resourceId = getResources().getIdentifier(
                    "status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId) * 2;
            }
            CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
            params.topMargin -= result;
            toolbar.setLayoutParams(params);
        }
        setSupportActionBar(toolbar);
    }


    @Override
    public void setAdapter() {

        List<String> title = new ArrayList<String>();
        title.add("消息");
        title.add("好友");
        title.add("动态");
        title.add("设置");

        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());
        fragments.add(new FragmentFour());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), title, fragments);

        vp_main.setAdapter(fragmentAdapter);

        tableLayout.addTab(tableLayout.newTab().setText(title.get(0)));
        tableLayout.addTab(tableLayout.newTab().setText(title.get(1)));
        tableLayout.addTab(tableLayout.newTab().setText(title.get(2)));
        tableLayout.addTab(tableLayout.newTab().setText(title.get(3)));

        tableLayout.setTabTextColors(getResources().getColor(R.color.material_blue_grey_950), getResources().getColor(R.color.material_orange_500));
        tableLayout.setupWithViewPager(vp_main);
        tableLayout.setTabsFromPagerAdapter(fragmentAdapter);

    }


    @Override
    public void setListener() {
        ActionBarDrawerToggle drawerListener = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                toolbar.setTitle(R.string.drawer_open);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                toolbar.setTitle(R.string.drawer_close);
            }
        };
        drawerListener.syncState();
        drawer.setDrawerListener(drawerListener);


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideViews() {
        Log.e(TAG, "hideViews....");
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
        fab_action.animate().translationY(fab_action.getHeight() + 100).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public void showViews() {
        Log.e(TAG, "showViews....");
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        fab_action.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
}
