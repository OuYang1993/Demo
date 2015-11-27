package com.ouyang.design.app.fragmet;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouyang.design.app.MainActivity;
import com.ouyang.design.app.R;
import com.ouyang.design.app.adapter.RecyclerAdapter;
import com.ouyang.design.app.listener.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {


    private View view;
    private RecyclerView rv_list;
    private Context context;
    private final String TAG = FragmentOne.class.getSimpleName();

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_one, container, false);
        initView();
        return view;
    }

    private void initView() {
        rv_list = (RecyclerView) view.findViewById(R.id.rv_list);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
        setListener();
    }

    private void setAdapter() {
        rv_list = (RecyclerView) view.findViewById(R.id.rv_list);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        rv_list.setLayoutManager(manager);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("never give up !!!  " + i);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(context, list);
        rv_list.setAdapter(adapter);
    }

    private void setListener() {
        rv_list.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                Log.e(TAG, "onHide...");
                ((MainActivity) context).hideViews();
            }

            @Override
            public void onShow() {
                Log.e(TAG, "onShow`````...");
                ((MainActivity) context).showViews();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
    }
}
