package com.ouyang.demo2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ouyang.demo2.R;
import com.ouyang.demo2.bean.User;
import com.ouyang.demo2.databinding.ItemUserBinding;
import com.ouyang.demo2.util.ToastUtil;

import java.util.List;

/**
 * Created by OuYang on 2016/7/5.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> list;

    public UserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemUserBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = ItemUserBinding.bind(itemView);
            binding.layoutContainer.setOnClickListener(this);
        }

        public void bind(@NonNull User user) {
            binding.setUser(user);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.layout_container:
                    ToastUtil.shortToast(list.get(getAdapterPosition()).name.get());
                    break;
            }
        }
    }
}
