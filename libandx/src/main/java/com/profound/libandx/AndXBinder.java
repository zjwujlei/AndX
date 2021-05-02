package com.profound.libandx;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.profound.libandx.R;
import com.profound.libandx.adapter.ButtonAdapter;
import com.profound.libandx.adapter.CheckBoxAdapter;
import com.profound.libandx.adapter.EditTextAdapter;
import com.profound.libandx.adapter.ImageViewAdapter;
import com.profound.libandx.adapter.ListViewAdapter;
import com.profound.libandx.adapter.TextViewAdapter;
import com.profound.libandx.adapter.ViewAdapter;
import com.profound.libandx.adapter.ViewGroupAdapter;

import io.reactivex.functions.Consumer;

/**
 * Created by wujinglei on 2018/12/10.
 */

public abstract class AndXBinder {
    /**
     * 获取CheckBox对应的Adapter
     * @param view
     * @return
     */
    protected CheckBoxAdapter getAdapter(CheckBox view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            CheckBoxAdapter mAdapter = new CheckBoxAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (CheckBoxAdapter) obj;
        }
    }

    /**
     * Button
     * @param view
     * @return
     */
    protected ButtonAdapter getAdapter(Button view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            ButtonAdapter mAdapter = new ButtonAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (ButtonAdapter) obj;
        }
    }

    /**
     * EditText
     * @param view
     * @return
     */
    protected EditTextAdapter getAdapter(EditText view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            EditTextAdapter mAdapter = new EditTextAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (EditTextAdapter) obj;
        }
    }

    /**
     * TextView
     * @param view
     * @return
     */
    protected TextViewAdapter getAdapter(TextView view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            TextViewAdapter mAdapter = new TextViewAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (TextViewAdapter) obj;
        }
    }

    /**
     * ImageView
     * @param view
     * @return
     */
    protected ImageViewAdapter getAdapter(ImageView view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            ImageViewAdapter mAdapter = new ImageViewAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (ImageViewAdapter) obj;
        }
    }

    /**
     * ListView
     * @param view
     * @return
     */
    protected ListViewAdapter getAdapter(ListView view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            ListViewAdapter mAdapter = new ListViewAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (ListViewAdapter) obj;
        }
    }

    /**
     * ViewGroup
     * @param view
     * @return
     */
    protected ViewGroupAdapter getAdapter(ViewGroup view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            ViewGroupAdapter mAdapter = new ViewGroupAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (ViewGroupAdapter) obj;
        }
    }

    /**
     * View
     * @param view
     * @return
     */
    protected ViewAdapter getAdapter(View view){
        Object obj = view.getTag(R.id.ANDX_ADAPTER_KEY);
        if(obj == null){
            ViewAdapter mAdapter = new ViewAdapter(getContext(),view);
            view.setTag(R.id.ANDX_ADAPTER_KEY,mAdapter);
            return mAdapter;
        }else{
            return (ViewAdapter) obj;
        }
    }

    /**
     *
     * bind {@param onNext} with {@param id} ,where data changed,onNext.accept will be called.
     *
     * first,we should get the value and call onNext.accept.this step we check the type of value.
     * this require a boolean value,if data by {@param id} don`t match,
     * throw a DataCastException.
     *
     * so when data changed,we can sure the type is right.
     *
     * @param id the dataId,stateId or computeId.
     * @param onNext data change callback.
     * @param <T> require data Type.
     */
    protected <T> void bind(int id, Consumer<T> onNext){
        getContext().subscribeAndX(id,onNext);
    }

    public abstract AndXContextWrapper getContext();

}
