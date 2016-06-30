 package com.ouyang.demo.app.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.MyPagerAdapter;
import com.ouyang.demo.app.fragment.FragmentOne;
import com.ouyang.demo.app.fragment.FragmentThree;
import com.ouyang.demo.app.fragment.FragmentTwo;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vp_main;
    private Button btn_1, btn_2, btn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        setAdapter();
    }

    private void setAdapter() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new FragmentTwo());
        list.add(new FragmentThree());
        list.add(new FragmentOne());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), list);
        vp_main.setAdapter(adapter);
    }

    private void initView() {
        vp_main = (ViewPager) findViewById(R.id.vp_main);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
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
