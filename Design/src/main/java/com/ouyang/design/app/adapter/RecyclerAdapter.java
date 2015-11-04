package com.ouyang.design.app.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ouyang.design.app.DetailActivity;
import com.ouyang.design.app.R;

import java.util.List;

/**
 * Created by oylz on 2015/6/19.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<String> list;
    private int SCALE_DELAY = 30;

    public RecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String s = list.get(position);
        holder.tv_item.setText(s);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, holder.container, "share");
                    context.startActivity(intent, options.toBundle());
                } else {
                    Intent intent = new Intent(context, DetailActivity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item;
        private CardView container;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_recycler_item);
            container = (CardView) itemView.findViewById(R.id.item_container);
        }
    }
}
