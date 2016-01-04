package com.ouyang.demo.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;
import com.ouyang.demo.app.DetailActivity;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by oylz on 2015/5/25.
 * 测试recyclerView 嵌套 recyclerView
 */
public class MultiRecyclerAdapter extends RecyclerView.Adapter<MultiRecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;
    private boolean canceled;
    private int deletedPosition;
    private String deletedItem;
    private final String TAG = MultiRecyclerAdapter.class.getSimpleName();
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 0;

    public MultiRecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        Log.e(TAG, "onCreateViewHolder=============viewType: " + viewType);
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.header, null);
        }
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);
//        handleSwiping(view, viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String s = list.get(position);
        int viewType = getItemViewType(position);
        Log.e(TAG, "onBindViewHolder=============viewType: " + viewType);
        if (viewType == TYPE_ITEM) {
            holder.textView.setText(s);
            holder.layout_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            final GridLayoutManager manager = new GridLayoutManager(context, 4);
            holder.rv_list.setLayoutManager(manager);



            List<String> list = new ArrayList<String>();
            for (int i = 0; i < 30; i++) {
                list.add("subItem " + i);
            }
            final RecyclerAdapter1 adapter = new RecyclerAdapter1(context, list);
            holder.rv_list.setAdapter(adapter);
            holder.rv_list.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            //调用以下方法让RecyclerView的第一个条目仅为1列
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //如果位置是0，那么这个条目将占用SpanCount()这么多的列数，再此也就是3
                    //而如果不是0，则说明不是Header，就占用1列即可
                    return getItemViewType(position)==TYPE_HEADER ? manager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public CardView layout_container;
        public RecyclerView rv_list;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.tv_content);
                layout_container = (CardView) itemView.findViewById(R.id.layout_container);
            } else {
                rv_list = (RecyclerView) itemView.findViewById(R.id.rv_item_list);
            }
        }
    }
}
