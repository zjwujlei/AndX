package com.profound.libandx.adapter;

import android.widget.Button;

import com.profound.libandx.AndXContextWrapper;

/**
 * create by wujl on 2018/12/18 下午5:57
 */
public class ButtonAdapter extends TextViewAdapter {

    private Button mView;

    public ButtonAdapter(AndXContextWrapper context, Button view) {
        super(context, view);
        this.mView = view;
    }

    @Override
    public Button getView() {
        return mView;
    }
}
