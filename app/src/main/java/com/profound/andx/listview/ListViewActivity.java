package com.profound.andx.listview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.profound.andx.BaseActivity;
import com.profound.andx.R;

public class ListViewActivity extends BaseActivity implements View.OnClickListener {

    public ListView mListView;
    public TextView mAddTv;
    public TextView mResetTv;

    private ListActionRegistry mRegistry;
    private ListBinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_layout);
        mListView = findViewById(R.id.activity_list);
        mAddTv = findViewById(R.id.list_add_tv);
        mAddTv.setOnClickListener(this);
        mResetTv = findViewById(R.id.list_reset_tv);
        mResetTv.setOnClickListener(this);

        ListLogicContext logic = new ListLogicContext()
                .attachActivity(this)
                .finish();

        mRegistry = new ListActionRegistry(logic);
        mBinder = new ListBinder(this,logic);
        mRegistry.setBinder(mBinder);

        mRegistry.commit(mRegistry.MUTATION_DATASET_INIT);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.list_add_tv){
            mRegistry.commit(mRegistry.MUTATION_DATASET_ADD,"ext item");
        }else if(v.getId() == R.id.list_reset_tv){
            mRegistry.commit(mRegistry.MUTATION_DATASET_RESET);
        }
    }

}
