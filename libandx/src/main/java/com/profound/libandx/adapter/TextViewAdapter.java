package com.profound.libandx.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.error.AssertInfo;

import io.reactivex.functions.Consumer;

/**
 * Created by wujinglei on 2018/11/21.
 */

public class TextViewAdapter extends ViewAdapter implements TextWatcher {

    private TextView mView;

    public TextViewAdapter(AndXContextWrapper context, TextView view) {
        super(context, view);
        this.mView = view;
        mView.addTextChangedListener(this);
    }

    private int textBindId = -1;

    /**
     * bind text with id.
     *
     * @see ViewAdapter#bindVisible(int)
     *
     * @param id
     */
    public void bindText(int id){
        textBindId = id;
        mContext.subscribeAndX(id, new Consumer<String>() {
            @Override
            public void accept(String value) throws Exception {
                if(value != null){
                    if(!value.equals(mView.getText().toString())){
                        mView.setText(value);
                    }
                }else{
                    AssertInfo.assertNullState(id);
                }
            }
        });

    }

    /**
     * bind text with id.
     *
     * @see ViewAdapter#bindVisible(int)
     * @param id
     */
    public void bindTextRes(int id){
//        Integer value = mContext.getAndXValue(id,Integer.class);
//        if(value != null){
//            mView.setText(value);
//        }else{
//            AssertInfo.assertNullState(id);
//        }
        mContext.subscribeAndX(id, new Consumer<Integer>() {
            @Override
            public void accept(Integer value) throws Exception {
                if(value != null){
                    mView.setText(value);
                }else{
                    AssertInfo.assertNullState(id);
                }
            }
        });
    }

    /**
     *
     * @param id
     */
    public void bindStyle(int id){
//        ViewStyle style = mContext.getAndXValue(id,ViewStyle.class);
//        if(style != null){
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
                }else{
                    AssertInfo.assertNullState(id);
                }
            }
        });

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(textBindId != -1){
            System.out.println("afterTextChanged");
            mContext.putState(textBindId,s.toString());
        }
    }

    @Override
    public TextView getView() {
        return mView;
    }


}
