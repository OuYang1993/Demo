package com.ouyang.demo.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ViewHolder;

import java.util.List;

/**
 * Created by oylz on 2015/6/16.
 */
public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public ListAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        TextView tv_content = ViewHolder.get(convertView, R.id.tv_list_item);
        String item = getItem(position);
        tv_content.setText(item);
        return convertView;
    }
}
