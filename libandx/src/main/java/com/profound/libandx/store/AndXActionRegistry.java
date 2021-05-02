package com.profound.libandx.store;

import com.profound.libandx.AndXContextWrapper;

import java.util.ArrayList;

/**
 * we can`t direct contact with state,use {#getAction()} to get AndXCallback,then change state.
 * AndXAction is the response for state change event.
 *
 */
public abstract class AndXActionRegistry {
    private final ArrayList<AndXAction> mActions = new ArrayList<>();

    public int addAction(AndXAction action){
        synchronized (mActions){
            int nextId = mActions.size();
            mActions.add(action);
            return nextId;
        }
    }

    public void commit(int actionId,Object... args){
        if(actionId< mActions.size()){
            mActions.get(actionId).action(args);
        }
    }

    /**
     * 获取指定的逻辑单元AndXContextWrapper
     *
     * @param clazz AndXContextWrapper的子类
     * @param <T>
     * @return
     */
    public <T extends AndXContextWrapper> T getAndXContext(Class<T> clazz){
        AndXContextWrapper current = getCurrent();
        while(current != null){
            if(clazz.isInstance(current)){
                return clazz.cast(current);
            }
            current = current.getParent();
        }
        return null;
    }

    protected abstract AndXContextWrapper getCurrent();
}
