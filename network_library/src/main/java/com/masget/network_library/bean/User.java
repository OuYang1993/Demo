package com.masget.network_library.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.masget.network_library.BR;

/**
 * Created by OuYang on 2/14/2016.
 * 登录用户信息
 * DataBinding 同步对象,新增属性需在get方法加上@Bindable注解,set方法加上 notifyPropertyChanged(BR.xxx);(xxx为对应属性名)
 * 另外务必在copy方法中加入新增的变量
 * 变量名与服务器返回的一致,方便用gson解析
 */
public class User extends BaseObservable {

    private String loginTip;

    private String loginname;
    private String password;
    private String session;
    private String appkey;
    private String mobilephone;
    private String staffid;


    @Bindable
    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
        notifyPropertyChanged(BR.loginname);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
        notifyPropertyChanged(BR.session);
    }

    @Bindable
    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
        notifyPropertyChanged(BR.appkey);
    }

    @Bindable
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
        notifyPropertyChanged(BR.mobilephone);
    }


    /**
     * 将新对象里面的值复制到原始对象中
     * 在dataBinding中因为 user= newUser 会导致user不是原始绑定的user,造成 无法同步的问题
     * 故需要将新对象的值复制到原始对象中,确保对象同步显示正常
     *
     * @param original 原始对象
     * @param newUser  新对象
     */
    public void copy(User original, User newUser) {
        original.setLoginname(newUser.getLoginname());
        original.setPassword(newUser.getPassword());
        original.setMobilephone(newUser.getMobilephone());
        original.setSession(newUser.getSession());
        original.setAppkey(newUser.getAppkey());
    }

    @Bindable
    public String getStaffid() {
        return staffid;
    }


    public void setStaffid(String staffid) {
        this.staffid = staffid;
        notifyPropertyChanged(BR.staffid);
    }

    @Override
    public String toString() {
        return "User{" +
                "loginname='" + loginname + '\'' +
                ", password='" + password + '\'' +
                ", session='" + session + '\'' +
                ", appkey='" + appkey + '\'' +
                ", staffid='" + staffid + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                '}';
    }

    public String getLoginTip() {
        return "请登陆";
    }
}
