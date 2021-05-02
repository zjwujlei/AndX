package com.profound.andx.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.profound.libandx.adapter.IDataSetChangeable;

import java.util.List;

public class ListActivityAdapter extends BaseAdapter implements IDataSetChangeable<List<String>> {
    private List<String> mDataSet;
    private Context mContext;
    public ListActivityAdapter(Context context,List<String> dataSet){
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = new TextView(mContext);
        }
        ((TextView)convertView).setText(mDataSet.get(position));
        return convertView;
    }

    @Override
    public void dataSetChange(List<String> d) {
        mDataSet = d;
    }
}
