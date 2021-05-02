package com.profound.andx.listview;

import android.widget.BaseAdapter;
import com.profound.libandx.AndXBinder;
import com.profound.libandx.AndXContextWrapper;

import java.util.List;

public class ListBinder extends AndXBinder {
    private ListLogicContext mLogic;
    private ListViewActivity mHolder;
    public ListBinder(ListViewActivity holder,ListLogicContext logic) {
        this.mLogic = logic;
        this.mHolder = holder;
    }

    @Override
    public AndXContextWrapper getContext() {
        return mLogic;
    }

    public void bindView(){
        BaseAdapter adapter = new ListActivityAdapter(
                mLogic,
                (List<String>)mLogic.getAndXValue(mLogic.DATA_SET));
        getAdapter(mHolder.mListView).bindAdapter(mLogic.DATA_SET,adapter);

    }
}
