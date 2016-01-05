package com.ouyang.databinding.entity;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

/**
 * Created by OuYang on 2016/1/4.
 */
public class ObserveUser {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> sex = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableInt age = new ObservableInt();
}
