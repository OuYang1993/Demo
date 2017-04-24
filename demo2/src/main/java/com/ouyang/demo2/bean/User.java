package com.ouyang.demo2.bean;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by OuYang on 2016/7/5.
 */
public class User {
    public ObservableInt id = new ObservableInt();
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableInt age = new ObservableInt();
}
