package com.example.objectanimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.objectanimator.databinding.ActivityMainBinding;
import com.example.objectanimator.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate();
            }
        });
    }

    private void setListener() {
        binding.fab1.setOnClickListener(this);
    }

    private void animate() {
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        PropertyValuesHolder pvhTranslationX = PropertyValuesHolder.ofFloat("translationX", binding.fab1.getTranslationX(), -200f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(binding.fab1, pvhAlpha, pvhScaleX, pvhTranslationX, pvhScaleY);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                binding.fab1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ToastUtil.shortToast("动画结束");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab1:
                ToastUtil.shortToast("小按钮");
                break;
        }
    }
}
