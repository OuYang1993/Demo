package com.ouyang.demo.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;
import com.ouyang.demo.app.R;
import com.ouyang.demo.app.utils.ToastUtil;

import java.util.List;

/**
 * Created by oylz on 2015/5/26.
 */
public class SwipeAdapter extends BaseSwipeAdapter {

    private Context context;
    private List<String> list;
    private final String TAG = SwipeAdapter.class.getSimpleName();
    private int deletedPosition = -1;
    private String deletedItem;
    private boolean canceled = false;

    public SwipeAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.layout_list_item, viewGroup, false);
    }


    @Override
    public void fillValues(final int position, final View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.text_data);
        Button btn_delete = (Button) view.findViewById(R.id.btn_delete);

        String item = getItem(position);
        tv_title.setText(item);
        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe);
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                super.onOpen(layout);
                Log.d(TAG, "item opened");
            }

            @Override
            public void onClose(SwipeLayout layout) {
                super.onClose(layout);
                Log.d(TAG, "item closed");
            }
        });


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.shortToast("delete item " + position);
                deletedPosition = position;
                deletedItem = list.get(position);
                closeItem(position);
                list.remove(position);//从当前列表删除, 并没有访问网络真正删除数据
                notifyDataSetChanged();


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
                                            notifyDataSetChanged();
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
        });
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


}
