package com.profound.libandx.adapter;

import android.widget.EditText;
import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.error.AssertInfo;

import io.reactivex.functions.Consumer;

/**
 * Created by wujinglei on 2018/11/27.
 */

public class EditTextAdapter extends TextViewAdapter {

    private EditText mView;
    private AndXContextWrapper mContext;

    public EditTextAdapter(AndXContextWrapper context, EditText view) {
        super(context, view);
        this.mView = view;
        this.mContext = context;
        mView.addTextChangedListener(this);
    }

    @Override
    public EditText getView() {
        return mView;
    }

    @Override
    public void bindStyle(int id) {
//        ViewStyle style = mContext.getAndXValue(id,ViewStyle.class);
//        if(style!=null){
//            if(style.textColor != 0){
//                mView.setTextColor(mContext.getResources().getColor(style.textColor));
//            }
//            if(style.backgroundColor != 0){
//                mView.setBackgroundColor(mContext.getResources().getColor(style.backgroundColor));
//            }
//            if(style.backgroundImage != 0){
//                mView.setBackgroundResource(style.backgroundImage);
//            }
//            if(style.maxLine != 0){
//                mView.setMaxLines(style.maxLine);
//            }
//            if(style.inputType != 0){
//                mView.setInputType(style.inputType);
//            }
//        }else{
//            AssertInfo.assertNullState(id);
//        }
        mContext.subscribeAndX(id, new Consumer<ViewStyle>() {
            @Override
            public void accept(ViewStyle style) throws Exception {
                if(style != null){
                    if(style.textColor != 0){
                       mView.setTextColor(mContext.getResources().getColor(style.textColor));
                    }
                    if(style.backgroundColor != 0){
                       mView.setBackgroundColor(mContext.getResources().getColor(style.backgroundColor));
                    }
                    if(style.backgroundImage != 0){
                       mView.setBackgroundResource(style.backgroundImage);
                    }
                    if(style.maxLine != 0){
                       mView.setMaxLines(style.maxLine);
                    }
                    if(style.inputType != 0){
                       mView.setInputType(style.inputType);
                    }
                }else{
                    AssertInfo.assertNullState(id);
                }
            }
        });
    }
}
