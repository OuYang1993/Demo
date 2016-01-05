package com.ouyang.databinding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;
import com.ouyang.databinding.R;
import com.ouyang.databinding.databinding.UserItemLayoutBinding;
import com.ouyang.databinding.entity.User;
import com.ouyang.databinding.util.ToastUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by OuYang on 2016/1/4.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private List<User> list;
    private final String TAG = UserAdapter.class.getSimpleName();
    private int deletedPosition = -1;
    private User deletedItem;
    private boolean canceled;

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.tv_index.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onItemDismiss(int position, int direction) {
        deletedItem = list.get(position);
        deletedPosition = position;
        list.remove(position);
        notifyItemRemoved(position);
        if (direction == ItemTouchHelper.LEFT) {//LEFT 表示滑县向左边
            Snackbar snackBar = Snackbar.with(context).text("删除")
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
                            if (!canceled) {//没有执行取消操作
                                Log.e("snackBar", "confirm deleted");
                            } else {//取消删除操作
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
                    });
            SnackbarManager.show(snackBar);
        } else if (direction == ItemTouchHelper.RIGHT) {
            //任务结束
            ToastUtil.shortToast("任务已存档");
        }
    }

    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private UserItemLayoutBinding binding;
        private CardView layout_container;
        private TextView tv_index;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = UserItemLayoutBinding.bind(itemView);
            layout_container = binding.itemContainer;
            tv_index = binding.tvIndex;
            layout_container.setOnClickListener(this);
        }

        public void bind(@NonNull User user) {
            binding.setUser(user);
        }

        @Override
        public void onClick(View v) {
            User user = list.get(getAdapterPosition());
            ToastUtil.shortToast(user.toString());
            Log.e(TAG, "user info : " + user.toString());
        }
    }
}
