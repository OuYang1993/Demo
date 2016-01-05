package com.ouyang.databinding.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Log;

import com.ouyang.databinding.BR;

/**
 * Created by OuYang on 2016/1/4.
 */
public class SyncUser extends BaseObservable {
    private String name;
    private String sex;
    private int age;
    private String address;
    private String errorName = "姓名不符合规则";

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);

    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    public boolean isNameAvailable() {
        return name != null && name.length() <= 10;
    }

    public String getErrorName() {
        return errorName;
    }

}
