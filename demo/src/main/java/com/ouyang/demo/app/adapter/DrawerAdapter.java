package com.ouyang.demo.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ToastUtil;

import java.util.List;

/**
 * Created by oylz on 2015/5/25.
 */
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_HEADER = 0;

    public DrawerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new MyViewHolder(view, viewType);
        } else if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.drawer_header, parent, false);
            return new MyViewHolder(view, viewType);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (holder.type == TYPE_ITEM) {
            final String s = list.get(position - 1);
            holder.textView.setText(s);
            holder.iv_order.setVisibility(View.GONE);
            holder.layout_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.shortToast(s);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_HEADER)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private int type;

        private TextView textView;
        private CardView layout_container;
        private ImageView iv_order;

        public MyViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.tv_content);
                layout_container = (CardView) itemView.findViewById(R.id.layout_container);
                iv_order = (ImageView) itemView.findViewById(R.id.iv_order);
                type = TYPE_ITEM;
            } else if (viewType == TYPE_HEADER) {
                type = TYPE_HEADER;
            }
        }
    }
}
