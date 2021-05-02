package com.profound.libandx.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.error.AssertInfo;

import io.reactivex.functions.Consumer;

public class ViewGroupAdapter extends ViewAdapter{
    private ViewGroup mGroup;

    public ViewGroupAdapter(AndXContextWrapper context, ViewGroup group) {
        super(context, group);
        this.mGroup = group;
    }

    /**
     * add child for {@link ViewGroup}
     * @param id
     */
    public void bindChild(int id){
//        View value = mContext.getAndXValue(id,View.class);
//        if(value != null){
//            mGroup.removeAllViews();
//            mGroup.addView(value);
//        }else{
//            AssertInfo.assertNullState(id);
//        }
        mContext.subscribeAndX(id, new Consumer<View>() {
            @Override
            public void accept(View value) throws Exception {
                if(value != null){
                    mGroup.removeAllViews();
                    mGroup.addView(value);
                }else{
                    AssertInfo.assertNullState(id);
                }
            }
        });
    }
}
