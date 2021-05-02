package com.profound.libandx.adapter;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.profound.libandx.AndXContextWrapper;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ListViewAdapter extends ViewGroupAdapter{
    private ListView mView;
    private BaseAdapter mAdapter;
    private WeakReference<Object> weakRef;
    public ListViewAdapter(AndXContextWrapper context, ListView view) {
        super(context, view);
        this.mView = view;
    }

    public void bindAdapter(int id,BaseAdapter adapter){
        weakRef = new WeakReference<>(mContext.getAndXValue(id,List.class));
        this.mAdapter = adapter;
        mView.setAdapter(adapter);
        mContext.subscribeAndX(id, new Consumer<List>() {
            @Override
            public void accept(List value) throws Exception {

                if(weakRef.get()!=null && weakRef.get().hashCode() == value.hashCode()) {
                    mAdapter.notifyDataSetChanged();
                }else{
                    weakRef = new WeakReference<>(value);
                    if (mAdapter instanceof IDataSetChangeable) {
                        try {
                            ((IDataSetChangeable) mAdapter).dataSetChange(value);
                            mAdapter.notifyDataSetInvalidated();
                        } catch (Exception e) {

                        }
                    }
                }

            }
        });
    }
}
