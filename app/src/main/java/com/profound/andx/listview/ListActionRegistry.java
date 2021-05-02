package com.profound.andx.listview;

import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.store.AndXAction;
import com.profound.libandx.store.AndXActionRegistry;

import java.util.ArrayList;
import java.util.List;

public class ListActionRegistry extends AndXActionRegistry {
    private ListLogicContext mLogic;
    private ListBinder mBinder;

    public ListActionRegistry(ListLogicContext logic){
        this.mLogic = logic;
    }

    public void setBinder(ListBinder binder){
        this.mBinder = binder;
    }

    @Override
    protected AndXContextWrapper getCurrent() {
        return mLogic;
    }

    private List<String> mDataSet = new ArrayList<>();

    public int MUTATION_DATASET_INIT = addAction(new AndXAction() {
        @Override
        public void action(Object... args) {
            for(int i=0;i<30;i++){
                mDataSet.add("item:"+i);
            }
            mLogic.putState(mLogic.DATA_SET,mDataSet);
            mBinder.bindView();
        }
    });

    public int MUTATION_DATASET_ADD = addAction(new AndXAction() {
        @Override
        public void action(Object... args) {
            mDataSet.add((String) args[0]);
            mLogic.updateState(mLogic.DATA_SET);
        }
    });

    public int MUTATION_DATASET_RESET = addAction(new AndXAction() {
        @Override
        public void action(Object... args) {
            mDataSet = new ArrayList<>();
            for(int i=0;i<30;i++){
                mDataSet.add("reset item:"+i);
            }
            mLogic.putState(mLogic.DATA_SET,mDataSet);
        }
    });
}
