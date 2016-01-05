package com.ouyang.databinding.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ouyang.databinding.R;
import com.ouyang.databinding.databinding.ActivityNormalBinding;
import com.ouyang.databinding.entity.User;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_age, et_sex, et_address;
    private Button btn_generate;
    private ActivityNormalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_normal);
        setupActionBar();
        initView();
        setListener();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        et_name = binding.etName;
        et_sex = binding.etSex;
        et_age = binding.etAge;
        et_address = binding.etAddress;
        btn_generate = binding.btnGenerate;
    }

    private void setListener() {
        btn_generate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_generate:
                generateUser();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void generateUser() {
        User user = new User();
        String name = et_name.getText().toString();
        String ageString = et_age.getText().toString();
        String sex = et_sex.getText().toString();
        String address = et_address.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(ageString)) {
            Toast.makeText(this, "参数为空", Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageString);
        user.setName(name);
        user.setSex(sex);
        user.setAge(age);
        user.setAddress(address);
        binding.setUser(user);
    }
}
