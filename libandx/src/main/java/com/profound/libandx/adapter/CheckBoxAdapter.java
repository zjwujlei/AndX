package com.profound.libandx.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.error.AssertInfo;

import java.util.Map;

import io.reactivex.functions.Consumer;

public class CheckBoxAdapter extends ButtonAdapter implements View.OnClickListener {
    private CheckBox mView;
    private int bindId = -1;
    public CheckBoxAdapter(AndXContextWrapper context, CheckBox view) {
        super(context, view);
        this.mView = view;
        mView.setOnClickListener(this);
    }

    /**
     *
     * set check state for CheckBox
     *
     * @param id
     */
    public void bindCheck(int id){
//        Map value = mContext.getAndXValue(id,Map.class);
//        bindId = id;
//        if(value!=null){
//            Object obj = value.get(mView.getId());
//            if(obj instanceof Boolean){
//                boolean isCheck = (Boolean) obj;
//                mView.setChecked(isCheck);
//            }
//        }else{
//            AssertInfo.assertNullState(id);
//
//        }
        mContext.subscribeAndX(id, new Consumer<Map>() {
            @Override
            public void accept(Map value) throws Exception {
                if(value != null){
                    Object obj = value.get(mView.getId());
                    if(obj instanceof Boolean){
                        boolean isCheck = (Boolean) obj;
                        if(mView.isChecked() != isCheck){
                            mView.setChecked(isCheck);
                        }
                    }
                }else{
                    AssertInfo.assertNullState(id);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Map value = mContext.getAndXValue(bindId,Map.class);
        for(Object obj:value.keySet()){
            value.put(obj,false);
        }
        value.put(mView.getId(),true);
        mContext.updateState(bindId);

    }
}
