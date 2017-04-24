package com.ouyang.demo2.activity;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.ouyang.demo2.R;
import com.ouyang.demo2.databinding.ActivityMainBinding;
import com.ouyang.demo2.fragment.AccountFragment;
import com.ouyang.demo2.fragment.ArchiveFragment;
import com.ouyang.demo2.fragment.LocationFragment;
import com.ouyang.demo2.fragment.MessageFragment;
import com.ouyang.demo2.fragment.SettingFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<Integer> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initFragments();
        initView();
        setListener();
    }

    private void initFragments() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new LocationFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new AccountFragment());
        fragmentList.add(new ArchiveFragment());
        fragmentList.add(new SettingFragment());

        colorList = new ArrayList<>();
        colorList.add(R.color.blue_grey_500);
        colorList.add(R.color.teal_500);
        colorList.add(R.color.green_500);
        colorList.add(R.color.colorPrimary);
        colorList.add(R.color.colorPrimary);
    }

    private void initView() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_tab_1, R.drawable.ic_location_on_white_24dp, colorList.get(0));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_tab_2, R.drawable.ic_message_white_24dp, colorList.get(1));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_tab_4, R.drawable.ic_account_circle_white_24dp, colorList.get(2));
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.title_tab_3, R.drawable.ic_archive_white_24dp, colorList.get(3));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem(R.string.title_tab_5, R.drawable.ic_settings_white_24dp, colorList.get(4));

        binding.navigationBottom.addItem(item1);
        binding.navigationBottom.addItem(item2);
        binding.navigationBottom.addItem(item3);
//        binding.navigationBottom.addItem(item4);
//        binding.navigationBottom.addItem(item5);

//        binding.navigationBottom.setNotification(4, 3);//设置消息提示(按钮右上角)
        binding.navigationBottom.setColored(true);
        binding.navigationBottom.setCurrentItem(0);
        switchFragment(0);
    }

    private void setListener() {
        binding.navigationBottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                switchFragment(position);
            }
        });
    }

    private void switchFragment(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setNavigationBarColor(getResources().getColor(colorList.get(position)));
        
        String tag = "fragment" + position;
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (fragment == null) {
            fragment = fragmentList.get(position);
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.add(R.id.fragment_container, fragment, "fragment" + position);
        }

        //显示当前选中的页面,隐藏其他页面
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (position == i)
                transaction.show(f);
            else
                transaction.hide(f);
        }

        transaction.commit();
    }
}
