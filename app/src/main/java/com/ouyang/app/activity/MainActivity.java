package com.ouyang.app.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ouyang.app.R;
import com.ouyang.app.adapter.PagerAdapter;
import com.ouyang.app.databinding.ActivityMainBinding;
import com.ouyang.app.fragment.FirstFragment;
import com.ouyang.app.fragment.SecondFragment;
import com.ouyang.app.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setAdapter();
    }

    private void setAdapter() {
        List<Fragment> list = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        titles.add("第一页");
        titles.add("第二页");
        titles.add("第三页");

        list.add(new FirstFragment());
        list.add(new SecondFragment());
        list.add(new ThirdFragment());
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), list);
        binding.vpContent.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.vpContent);
        binding.vpContent.setOffscreenPageLimit(list.size());

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles.get(i));
                tab.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
            }
        }
    }
}
