package com.profound.libandx;

import android.app.Activity;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;

import com.profound.libandx.error.AssertError;
import com.profound.libandx.error.AssertInfo;
import com.profound.libandx.store.AndXComputeRegistry;
import com.profound.libandx.store.AndXForm;
import com.profound.libandx.store.AndXStateRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by wujinglei on 2018/11/20.
 */
public class AndXContext extends ContextWrapper{

    /**
     * context生命感知常量。未开启感知实现
     */
    public static final int CONTEXT_PERCEPTION_IDE = 0;
    /**
     * context生命感知常量。激活状态，状态变更时会显示的通知视图更新。
     */
    public static final int CONTEXT_PERCEPTION_ACTIVE = 1;
    /**
     * context生命感知常量。暂停状态，状态变更时会以静默模式处理只记录状态值变更，不通知观察者。
     * 再次感知到激活状态时，所有变更过的状态都会发送最新的值。
     */
    public static final int CONTEXT_PERCEPTION_PAUSE = 2;

    public static final int COMPUTE_ID_START = 0x800000;

    private final AndXStateRegistry mStateRegistry = new AndXStateRegistry();
    private final AndXComputeRegistry mComputeRegistry = new AndXComputeRegistry(this);
    protected Activity mActivity;
    private ReentrantLock mLock = new ReentrantLock();

    private List<Integer> mComputeDependenceCache = new ArrayList<>();

    private int mPerception = CONTEXT_PERCEPTION_IDE;

    AndXContext(Activity base) {
        super(base);
        this.mActivity = base;

    }

    public <T> int sInit(@NonNull T value) {
        int stateId = mStateRegistry.addState(value);
        return stateId;
    }

    public <T> void putState(int stateId, @NonNull T value) {
        mStateRegistry.pushValue(stateId,value,mPerception == CONTEXT_PERCEPTION_PAUSE);
    }

    public void updateState(int stateId){
        mStateRegistry.updateState(stateId,mPerception == CONTEXT_PERCEPTION_PAUSE);
    }
    private  <T> T getStateValue(int stateId){
        return mStateRegistry.getValue(stateId);
    }

    public void startEquation(int computeId){
        mLock.lock();
        AssertInfo.assertStartEquation(computeId);
        mComputeDependenceCache.clear();
    }

    protected  <T> T getState(int stateId){
        T t = mStateRegistry.getValue(stateId);
        AssertInfo.assertMarkDependance(stateId);
        if(!mComputeDependenceCache.contains(stateId)){
            mComputeDependenceCache.add(stateId);
        }
        return t;
    }

    protected int getIntState(int stateId){
        int t = mStateRegistry.getValue(stateId);
        AssertInfo.assertMarkDependance(stateId);
        if(!mComputeDependenceCache.contains(stateId)){
            mComputeDependenceCache.add(stateId);
        }
        return t;
    }

    protected int optIntState(int stateId,int defaultValue){
        Object t = mStateRegistry.getValue(stateId);
        try{
            if(t instanceof Integer){
                AssertInfo.assertMarkDependance(stateId);
                if(!mComputeDependenceCache.contains(stateId)){
                    mComputeDependenceCache.add(stateId);
                }
                return (int)t;
            }
        }catch (Throwable thr){
            thr.printStackTrace();
            AssertError.AssertStateCastException(stateId,t,Integer.class);
        }
        return defaultValue;
    }

    protected String optStringState(int stateId,String defaultValue){
        Object t = mStateRegistry.getValue(stateId);
        try{
            if(t instanceof String){
                AssertInfo.assertMarkDependance(stateId);
                if(!mComputeDependenceCache.contains(stateId)){
                    mComputeDependenceCache.add(stateId);
                }
                return (String) t;
            }
        }catch (Throwable thr){
            thr.printStackTrace();
            AssertError.AssertStateCastException(stateId,t,String.class);
        }
        return defaultValue;
    }

    protected String getStringState(int stateId){
        String t = mStateRegistry.getValue(stateId);
        AssertInfo.assertMarkDependance(stateId);
        if(!mComputeDependenceCache.contains(stateId)){
            mComputeDependenceCache.add(stateId);
        }
        return t;
    }

    public List<Integer> endEquation(int computeId){
        List<Integer> stateIds = new ArrayList<>();
        stateIds.addAll(mComputeDependenceCache);
        mComputeDependenceCache.clear();
        AssertInfo.assertEndEquation(computeId);
        mLock.unlock();
        return stateIds;
    }

    public <T> Observable<T> getStateObservable(int stateId, Class<T> clazz){
        return mStateRegistry.getObservable(stateId,clazz);
    }

    public <T> Observable<T> getStateObservable(int stateId){
        return mStateRegistry.getObservable(stateId);
    }

    private <T> boolean subscribeState(int stateId, Consumer<T> onNext){
        return mStateRegistry.subscribe(stateId,onNext);
    }

    public <T> int cInit(@NonNull AndXForm<T> form) {
        int computeId = mComputeRegistry.addCompute(form);
        return computeId;
    }


    public <T> int cInitStrict(@NonNull AndXForm<T> form) {
        int computeId = mComputeRegistry.addCompute(form,true);
        return computeId;
    }

    private <T> T getComputeValue(int computeId){
        return mComputeRegistry.getValue(computeId);
    }

    public <T> Observable<T> getComputeObservable(int computeId, Class<T> clazz){
        return mComputeRegistry.getObservable(computeId,clazz);
    }

    private <T> boolean subscribeCompute(int computeId,Consumer<T> onNext){
        return mComputeRegistry.subscribe(computeId,onNext);
    }

    public <T> AndXContext commitCompute() {
        mComputeRegistry.commit();
        return this;
    }

    public <T> boolean subscribeAndX(int id,Consumer<T> onNext){
        if(id >= COMPUTE_ID_START){
            return subscribeCompute(id,onNext);
        }else{
            return subscribeState(id,onNext);
        }

    }

    public <T> T getAndXValue(int id,Class<T> clazz){
        if(id >= COMPUTE_ID_START){
            Object obj = getComputeValue(id);
            if(obj == null){
                return null;
            }
            AssertError.AssertStateCastException(id,obj,clazz);
            return clazz.cast(obj);
        }else{
            Object obj = getStateValue(id);
            if(obj == null){
                return null;
            }
            AssertError.AssertComputeCastException(id,obj,clazz);
            return clazz.cast(obj);
        }

    }

    public <T> T getAndXValue(int id){
        if(id >= COMPUTE_ID_START){
            return getComputeValue(id);
        }else{
            return getStateValue(id);
        }

    }

    /**
     * context生命周期感知实现。与onPause配合而使用，对应页面的onResume方法。
     * 如不进行调用，不会开启感知功能，哪怕页面在后台也会进行视图更新。
     *
     */
    protected void onResume() {
        mPerception = CONTEXT_PERCEPTION_ACTIVE;
        mStateRegistry.notifyChanges();

    }

    /**
     * context生命周期感知实现。与onResume配合而使用，对应页面的onResume方法。
     * 如不进行调用，不会开启感知功能，哪怕页面在后台也会进行视图更新。
     *
     */
    protected void onPause() {
        mPerception = CONTEXT_PERCEPTION_PAUSE;
    }



}
