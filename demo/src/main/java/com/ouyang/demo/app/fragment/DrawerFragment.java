package com.ouyang.demo.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DrawerFragment extends Fragment {

    private RecyclerView rv_content;
    private View view;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_drawer, null);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setAdapter();
    }

    private void setAdapter() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("this is item " + i);
        }
        RecyclerAdapter adapter = new RecyclerAdapter(getActivity(), list);
        rv_content.setAdapter(adapter);
    }

    private void initView() {
        rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
    }
}
