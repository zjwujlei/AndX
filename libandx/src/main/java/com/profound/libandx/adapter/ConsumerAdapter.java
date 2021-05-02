package com.profound.libandx.adapter;

import com.profound.libandx.AndXContextWrapper;

import io.reactivex.functions.Consumer;

public class ConsumerAdapter {
    private AndXContextWrapper mContext;

    public ConsumerAdapter(AndXContextWrapper context){
        this.mContext = context;
    }

    public <T> void bind(int id, Consumer<T> onNext){
        mContext.subscribeAndX(id,onNext);
    }
}
