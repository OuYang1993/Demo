package com.ouyang.databinding.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ouyang.databinding.R;
import com.ouyang.databinding.databinding.ActivitySyncOneBinding;
import com.ouyang.databinding.entity.ObserveUser;
import com.ouyang.databinding.entity.SyncUser;
import com.ouyang.databinding.util.ToastUtil;

public class SyncOneActivity extends AppCompatActivity {

    private ActivitySyncOneBinding binding;
    private EditText et_name, et_sex, et_age, et_address;
    private EditText et_name2, et_sex2, et_age2, et_address2;
    private SyncUser user;
    private ObserveUser obUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sync_one);
        user = new SyncUser();
        binding.setUser(user);
        obUser = new ObserveUser();
        binding.setObUser(obUser);
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

        et_name2 = binding.etName2;
        et_sex2 = binding.etSex2;
        et_age2 = binding.etAge2;
        et_address2 = binding.etAddress2;
    }

    private void setListener() {
        et_address.addTextChangedListener(new MyTextWatcher(et_address));
        et_age.addTextChangedListener(new MyTextWatcher(et_age));
        et_sex.addTextChangedListener(new MyTextWatcher(et_sex));
        et_name.addTextChangedListener(new MyTextWatcher(et_name));

        et_address2.addTextChangedListener(new MyTextWatcher(et_address2));
        et_age2.addTextChangedListener(new MyTextWatcher(et_age2));
        et_sex2.addTextChangedListener(new MyTextWatcher(et_sex2));
        et_name2.addTextChangedListener(new MyTextWatcher(et_name2));
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


    private class MyTextWatcher implements TextWatcher {

        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            switch (editText.getId()) {
                case R.id.et_address:
                    user.setAddress(text);
                    break;
                case R.id.et_age:
                    user.setAge(TextUtils.isEmpty(text) ? 0 : Integer.parseInt(text));
                    break;

                case R.id.et_name:
                    user.setName(text);
                    break;

                case R.id.et_sex:
                    user.setSex(text);
                    break;

                case R.id.et_address2:
                    obUser.address.set(text);
                    break;
                case R.id.et_age2:
                    obUser.age.set(TextUtils.isEmpty(text) ? 0 : Integer.parseInt(text));
                    break;

                case R.id.et_name2:
                    obUser.name.set(text);
                    break;

                case R.id.et_sex2:
                    obUser.sex.set(text);
                    break;
            }
        }
    }


}
