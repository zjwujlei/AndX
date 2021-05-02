package com.profound.libandx.adapter;

import android.widget.ImageView;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.error.AssertError;
import com.profound.libandx.error.AssertInfo;

import io.reactivex.functions.Consumer;

public class ImageViewAdapter extends ViewAdapter{
    private ImageView mView;
    public ImageViewAdapter(AndXContextWrapper context, ImageView view) {
        super(context, view);
        this.mView = view;
    }

    public void bindSrc(int id){
//        Integer value = mContext.getAndXValue(id,Integer.class);
//        if(value != null){
//            mView.setImageResource(value);
//        }else{
//            AssertInfo.assertNullState(id);
//        }
        mContext.subscribeAndX(id, new Consumer<Integer>() {
            @Override
            public void accept(Integer value) throws Exception {
                if(value!=null){
                    mView.setImageResource(value);
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
//
//        if(style.backgroundColor != 0){
//            mView.setBackgroundColor(mContext.getResources().getColor(style.backgroundColor));
//        }
//        if(style.backgroundImage != 0){
//            mView.setBackgroundResource(style.backgroundImage);
//        }
//        if(style.scaleType != null){
//            mView.setScaleType(style.scaleType);
//        }
        mContext.subscribeAndX(id, new Consumer<ViewStyle>() {
            @Override
            public void accept(ViewStyle style) throws Exception {
                AssertError.AssertStateValid(id,style);
                if(style.backgroundColor != 0){
                    mView.setBackgroundColor(mContext.getResources().getColor(style.backgroundColor));
                }
                if(style.backgroundImage != 0){
                    mView.setBackgroundResource(style.backgroundImage);
                }
                if(style.scaleType != null){
                    mView.setScaleType(style.scaleType);
                }
            }
        });

    }

}
