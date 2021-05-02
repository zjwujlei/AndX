package com.profound.andx.login;

import android.databinding.BaseObservable;

/**
 * Created by wujinglei on 2018/11/21.
 */

public class User extends BaseObservable{
    private String name;
    private String pwd;

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
        notifyChange();
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return super.toString()+"[name"+name+",pwd:"+pwd+"]";
    }
}
