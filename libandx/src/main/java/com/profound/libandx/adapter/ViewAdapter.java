package com.profound.libandx.adapter;

import android.view.View;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.error.AssertError;
import com.profound.libandx.store.TransformFunc;

import io.reactivex.functions.Consumer;

/**
 * Created by wujinglei on 2018/11/21.
 *
 * delegate for {@link android.view.View}
 */

public class ViewAdapter {

    protected AndXContextWrapper mContext;
    private View mBase;

    public ViewAdapter(AndXContextWrapper context,View view){
        this.mContext = context;
        this.mBase = view;

    }

    /**
     * set whether {@link #mBase} visible,bind it with state called {@param name}.
     *
     * first,we should get the value and init view.this step,we check the type of value.
     * this require a boolean value,if data by {@param id} don`t match,
     * throw a DataCastException.
     *
     * so when data changed,we can sure the type is right.
     *
     * @param id bindId,stateId or computeId.
     *
     */
    public void bindVisible(int id){
        mContext.subscribeAndX(id, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean value) throws Exception {
                AssertError.AssertStateValid(id,value);
                mBase.setVisibility(value?View.VISIBLE:View.GONE);
            }
        });
    }

    public <I> void bindVisible(int id, TransformFunc<I,Boolean> func){
        mContext.subscribeAndX(id, new Consumer<I>() {
            @Override
            public void accept(I value) throws Exception {
                AssertError.AssertStateValid(id,value);
                mBase.setVisibility(func.transform(value)?View.VISIBLE:View.GONE);
            }
        });
    }

    /**
     * set whether {@link #mBase} hide,bind it with state named {@param id}.
     *
     * first,we should get the value and init view.this step,we check the type of value.
     * this require a boolean value,if data by {@param id} don`t match,
     * throw a DataCastException.
     *
     * so when data changed,we can sure the type is right.
     *
     * @param id bindId,stateId or computeId.
     *
     */
    public void bindHide(int id){
        mContext.subscribeAndX(id, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean value) throws Exception {
                AssertError.AssertStateValid(id,value);
                mBase.setVisibility(value?View.GONE:View.VISIBLE);

            }
        });
    }

    public <I> void bindHide(int id,TransformFunc<I,Boolean> func){
        mContext.subscribeAndX(id, new Consumer<I>() {
            @Override
            public void accept(I value) throws Exception {
                AssertError.AssertStateValid(id,value);
                mBase.setVisibility(func.transform(value)?View.GONE:View.VISIBLE);

            }
        });
    }


    /**
     *
     * @param id
     */
    public void bindStyle(int id){
        mContext.subscribeAndX(id, new Consumer<ViewStyle>() {
            @Override
            public void accept(ViewStyle style) throws Exception {
                AssertError.AssertStateValid(id,style);
                if(style.backgroundColor != 0){
                    mBase.setBackgroundColor(mContext.getResources().getColor(style.backgroundColor));
                }
                if(style.backgroundImage != 0){
                    mBase.setBackgroundResource(style.backgroundImage);
                }
            }
        });

    }

    /**
     *
     * @param id
     */
    public void bindEnable(int id){
        mContext.subscribeAndX(id, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean value) throws Exception {
                System.out.println("accept");
                mBase.setEnabled(value);
            }
        });

    }



    public View getView(){
        return mBase;
    }

}
