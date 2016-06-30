package com.ouyang.demo.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.*;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;
import com.ouyang.demo.app.activity.DetailActivity;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ToastUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by oylz on 2015/5/25.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<String> list;
    private Context context;
    private boolean canceled;
    private int deletedPosition;
    private String deletedItem;
    private final String TAG = RecyclerAdapter.class.getSimpleName();

    public RecyclerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
//        handleSwiping(view, viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String s = list.get(position);
        holder.textView.setText(s);
        holder.layout_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void onItemDismiss(int position, int direction) {
        deletedPosition = position;
        deletedItem = list.get(position);
        list.remove(position);
        notifyItemRemoved(position);


        if (direction == ItemTouchHelper.RIGHT) {
            ToastUtil.shortToast("该任务已完成");
        } else if (direction == ItemTouchHelper.LEFT) {
            SnackbarManager.show(Snackbar.with(context).text("删除")
                            .actionLabel("撤销")
                            .actionColorResource(R.color.material_yellow_500)
                            .animation(true)
                            .actionListener(new ActionClickListener() {
                                @Override
                                public void onActionClicked(Snackbar snackbar) {
                                    Log.e("snackBar", "onActionClicked");
                                    canceled = true;
                                }
                            })
                            .eventListener(new EventListener() {
                                @Override
                                public void onShow(Snackbar snackbar) {
                                    Log.e("snackBar", "onShow");
                                    canceled = false;

                                }

                                @Override
                                public void onShowByReplace(Snackbar snackbar) {
                                    Log.e("snackBar", "onShowByReplace");

                                }

                                @Override
                                public void onShown(Snackbar snackbar) {
                                    Log.e("snackBar", "onShown");

                                }

                                @Override
                                public void onDismiss(Snackbar snackbar) {
                                    Log.e("snackBar", "onDismiss");
                                    if (!canceled) {
                                        Log.e("snackBar", "confirm deleted");
                                    } else {
                                        Log.e("snackBar", "cancel delete");
                                        list.add(deletedPosition, deletedItem);
                                        notifyItemInserted(deletedPosition);
                                    }

                                }

                                @Override
                                public void onDismissByReplace(Snackbar snackbar) {
                                    Log.e("snackBar", "onDismissByReplace");

                                }

                                @Override
                                public void onDismissed(Snackbar snackbar) {
                                    Log.e("snackBar", "onDisMissed");
                                }
                            })
            );
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public CardView layout_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_content);
            layout_container = (CardView) itemView.findViewById(R.id.layout_container);
        }
    }
}
