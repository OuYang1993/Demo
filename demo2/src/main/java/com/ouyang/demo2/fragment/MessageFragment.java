package com.ouyang.demo2.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ouyang.demo2.R;
import com.ouyang.demo2.adapter.UserAdapter;
import com.ouyang.demo2.bean.User;
import com.ouyang.demo2.databinding.FragmentMessageBinding;
import com.ouyang.demo2.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private static final String TAG = MessageFragment.class.getSimpleName();
    private Context context;
    private FragmentMessageBinding binding;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LogUtil.e(TAG, "onCreateView ");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.e(TAG, "onActivityCreated ");
        initData();
    }

    private void initData() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            User user = new User();
            user.id.set(i);
            user.name.set("name " + i);
            user.age.set(i + 10);
            list.add(user);
        }

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.rvMessage.setLayoutManager(manager);

        UserAdapter adapter = new UserAdapter(context, list);
        binding.rvMessage.setAdapter(adapter);
    }
}
