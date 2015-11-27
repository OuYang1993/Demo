package com.ouyang.design.app.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by OuYang on 2015/11/27.
 * 处理列表滑动
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int HIDE_DISTANCE = 40;//滑动多少距离触发事件
    private int scrolledDistance = 0;//滑动距离
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > HIDE_DISTANCE && controlsVisible) {
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
        } else if (scrolledDistance < -HIDE_DISTANCE && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }

        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }

    }

    public abstract void onHide();

    public abstract void onShow();
}
