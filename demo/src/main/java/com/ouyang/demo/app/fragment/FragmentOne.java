package com.ouyang.demo.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;

import android.widget.AdapterView;
import android.widget.ListView;
import com.ouyang.demo.app.ListActivity;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.SwipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    private Context context;
    private ListView lv_message;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        AppCompatActivity activity = (AppCompatActivity) this.context;
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = activity.getSupportActionBar();
        supportActionBar.setTitle("消息");
        View customView = LayoutInflater.from(activity).inflate(R.layout.custom_action_bar, null);
        supportActionBar.setCustomView(customView);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lv_message = (ListView) view.findViewById(R.id.lv_message);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            list.add("this is item " + i + " swipe left to remove ");
        }
        SwipeAdapter adapter = new SwipeAdapter(context, list);
        lv_message.setAdapter(adapter);

        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }
}
