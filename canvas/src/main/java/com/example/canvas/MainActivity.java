package com.example.canvas;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.canvas.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private PaintView mView;
    private ActivityMainBinding binding;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setListener();
    }

    private void setListener() {
        binding.layoutSign.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                width = v.getWidth();
                height = v.getHeight();
                initDraw();
                binding.layoutSign.removeOnLayoutChangeListener(this);
            }
        });
    }


    public void initDraw() {
        mView = new PaintView(this);
        binding.layoutSign.addView(mView);
        mView.setFocusableInTouchMode(true);
        mView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Log.e("xxx","width: "+width+"\theight: "+height);
        mView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        mView.setFocusable(true);
        mView.requestFocus();
    }


    public class PaintView extends View {


        private Paint paint;
        private Canvas cacheCanvas;
        private Path path;
        private Bitmap cacheBitmap;


        public PaintView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public PaintView(Context context) {
            super(context);
//            setWillNotDraw(false);
            init();
        }


        private void init() {
            Log.e("canvas", "画板初始化");
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            path = new Path();
            cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas(cacheBitmap);
            cacheCanvas.drawColor(Color.RED);
        }

        public void clear() {
            if (cacheCanvas != null) {
                cacheCanvas.drawPaint(paint);
                paint.setColor(Color.BLACK);
                cacheCanvas.drawColor(Color.RED);
                path.reset();
                invalidate();
            }
        }


        @Override
        protected void onDraw(Canvas canvas) {
            Log.e("canvas", "画线");
            canvas.drawBitmap(cacheBitmap, 0, 0, null);
            canvas.drawPath(path, paint);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            int curW = cacheBitmap != null ? cacheBitmap.getWidth() : 0;
            int curH = cacheBitmap != null ? cacheBitmap.getHeight() : 0;
            if (curW >= w && curH >= h) {
                return;
            }

            if (curW < w)
                curW = w;
            if (curH < h)
                curH = h;

            Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
            Canvas newCanvas = new Canvas();
            newCanvas.setBitmap(newBitmap);
            if (cacheBitmap != null) {
                newCanvas.drawBitmap(cacheBitmap, 0, 0, null);
            }
            cacheBitmap = newBitmap;
            cacheCanvas = newCanvas;
        }

        private float cur_x, cur_y;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
//            Log.e("canvas", "onTouch xxxxxxxxx ");
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    cur_x = x;
                    cur_y = y;
                    path.moveTo(cur_x, cur_y);
                    break;
                }

                case MotionEvent.ACTION_MOVE: {
                    path.quadTo(cur_x, cur_y, x, y);
                    cur_x = x;
                    cur_y = y;
                    invalidate();
                    break;
                }

                case MotionEvent.ACTION_UP: {
                cacheCanvas.drawPath(path, paint);
                path.reset();
                    break;
                }
            }
//        draw(cacheCanvas);
            invalidate();
            return true;
        }

    }
}
