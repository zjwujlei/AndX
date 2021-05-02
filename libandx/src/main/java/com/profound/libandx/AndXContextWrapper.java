package com.profound.libandx;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.profound.libandx.error.AssertError;
import com.profound.libandx.error.AssertInfo;
import com.profound.libandx.store.AndXForm;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 *
 */
public class AndXContextWrapper extends AndXContext implements LifecycleObserver {

    private AndXContextWrapper parent;

    /**
     * 创建一个孤立的AndXContextWrapper。
     * 无法作为Context使用
     * 不支持生命周期自动感知。
     *
     * 不建议通过该构造器创建对象。
     */
    protected AndXContextWrapper(){
        super(null);
    }

    /**
     * 通过父逻辑单元parent对象创建一个AndXContextWrapper。
     * 将自动拥有parent单元的所有数据逻辑。
     * 可自动感知该parent.mActivity的生命周期，调用{@link #startLifecycle()}函数开启。
     *
     * 建议通过{@link #link(Class)}函数创建。
     *
     * @param parent
     */
    protected AndXContextWrapper(AndXContextWrapper parent){
        super(parent.mActivity);
        this.parent = parent;
    }

    /**
     * 为当前AndXContextWrapper attach一个Activity。
     * 至此会AndXContextWrapper将自动代理Activity对象。
     * 可自动感知该Activity的生命周期，调用{@link #startLifecycle()}函数开启。
     *
     * @param base
     */

    public AndXContextWrapper attachActivity(Activity base) {
        super.attachBaseContext(base);
        this.mActivity = base;
        return this;
    }

    /**
     *
     * 将当前的AndXContextWrapper对象作为父逻辑单元创建类型为clazz的子逻辑单元。
     *
     * @param clazz
     * @return
     */
    public AndXContextWrapper link(Class<? extends AndXContextWrapper> clazz){
        try {
            return clazz.getConstructor(AndXContextWrapper.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     *
     * 逻辑层创建完成。
     *
     * @param <W>
     * @return
     */
    public <W extends AndXContextWrapper> W finish(){
        startLifecycle();
        return (W)this;
    }

    public <T> int sInit(@NonNull T value) {
        int stateId = getHexPref()+super.sInit(value);
        return stateId;
    }

    public <T> void putState(int stateId, @NonNull T value) {

        if(checkId(stateId)){
            super.putState(stateId - getHexPref(),value);
        }else{
            if(parent != null){
                parent.putState(stateId,value);
            }else{
                AssertInfo.assertNullState(stateId);
            }
        }

        computeJobSchedule();
    }

    public void updateState(int stateId){
        if(checkId(stateId)){
            super.updateState(stateId - getHexPref());
        }else{
            if(parent != null){
                parent.updateState(stateId);
            }else{
                AssertInfo.assertNullState(stateId);
            }
        }
        computeJobSchedule();
    }

    protected  <T> T getState(int stateId){
        if(checkId(stateId)){
            return super.getState(stateId - getHexPref());
        }else{
            if(parent != null){
                return parent.getState(stateId);
            }else{
                AssertError.AssertWrapperNotFound((stateId>>8));
                return null;
            }
        }
    }

    protected int getIntState(int stateId){
        if(checkId(stateId)){
            return super.getIntState(stateId - getHexPref());
        }else{
            if(parent != null){
                return parent.getIntState(stateId);
            }else{
                AssertError.AssertWrapperNotFound((stateId>>8));
                return 0;
            }
        }
    }

    protected int optIntState(int stateId,int defaultValue){

        if(checkId(stateId)){
            return super.optIntState(stateId - getHexPref(),defaultValue);
        }else{
            if(parent != null){
                return parent.optIntState(stateId,defaultValue);
            }else{
                AssertError.AssertWrapperNotFound((stateId>>8));
            }
        }
        return defaultValue;
    }

    protected String optStringState(int stateId,String defaultValue){
        if(checkId(stateId)){
            return super.optStringState(stateId - getHexPref(),defaultValue);
        }else{
            if(parent != null){
                return parent.optStringState(stateId,defaultValue);
            }else{
                AssertError.AssertWrapperNotFound((stateId>>8));
            }
        }
        return defaultValue;
    }

    protected String getStringState(int stateId){
        if(checkId(stateId)){
            return super.getStringState(stateId - getHexPref());
        }else{
            if(parent != null){
                return parent.getStringState(stateId);
            }else{
                AssertError.AssertWrapperNotFound((stateId>>8));
                return "";
            }
        }
    }

    public <T> int cInit(@NonNull AndXForm<T> form) {
        int computeId = getHexPref()+super.cInit(form);
        return computeId;
    }


    public <T> int cInitStrict(@NonNull AndXForm<T> form) {
        int computeId = getHexPref()+super.cInitStrict(form);
        return computeId;
    }

    /**
     *
     * @param <T>
     * @return
     */
    public <T> AndXContext commitCompute() {
        super.commitCompute();
        if(parent != null){
            parent.commitCompute();
        }
        return this;
    }

    public <T> boolean subscribeAndX(int id,Consumer<T> onNext){
        if(checkId(id)){
            return super.subscribeAndX(id - getHexPref(),onNext);
        }else{
            if(parent != null){
                return parent.subscribeAndX(id,onNext);
            }else{
                AssertInfo.assertNullState(id);
                return false;
            }
        }

    }

    public <T> T getAndXValue(int id,Class<T> clazz){
        if(checkId(id)){
            return super.getAndXValue(id - getHexPref(),clazz);
        }else{
            if(parent != null){
                return parent.getAndXValue(id,clazz);
            }else{
                AssertError.AssertWrapperNotFound(getHexPref());
                return null;
            }
        }

    }

    public <T> T getAndXValue(int id){
        if(checkId(id)){
            return super.getAndXValue(id - getHexPref());
        }else{
            if(parent != null){
                return parent.getAndXValue(id);
            }else{
                AssertError.AssertWrapperNotFound(getHexPref());
                return null;
            }
        }
    }

    /**
     *
     * 实现有点类似与ClassLoader
     *
     * ContextWrapper中定义的属性、计算式的ID前缀。
     *
     * 顶级实现类为0x00。
     * 子实现类为0x01，以此类推。
     *
     * 同一层级的类具有相同值。
     *
     * 用来实现ContextWrapper的多层级时，状态、计算式的ID不会重复。
     *
     *
     * 获取状态、计算式时，会根据这个前缀进行判断是子类的还是父类的。
     *
     * 子实现必须进行重写。在父实现的基础上加上0x01<<8的diff。否则在获取属性的时候只会获取到父实现
     *
     * public int getHexPref(){
     *     return super.getHexPref()+0x01<<8;
     *}
     *
     * 以此来实现ContextWrapper的多层级是，子实现，父实现的属性、计算式可以分开定义，使用时又可以一起使用。
     */
    public int getHexPref(){
        if(parent == null){
            return 0x01000000;
        }else{
            return parent.getHexPref()+0x01000000;
        }
    }

    /**
     * 检查该ID是否是在该Context中定义的状态或计算式。
     * @param id
     * @return
     */
    private boolean checkId(int id){
        return getHexPref() + 0x10000000> id && id >= getHexPref();

    }

    public AndXContextWrapper getParent(){
        return parent;
    }


    private Disposable mDisposable;

    /**
     * 计算式对状态变更自动感知实现。每次状态变更后，都注册一个延时事件，
     * 16ms后调用{@link AndXContextWrapper#commitCompute()}函数。
     * 16ms内多次注册的延时事件会替换。
     */
    public void computeJobSchedule() {
        if(mDisposable != null && !mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        mDisposable = Observable.timer(16,TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                commitCompute();
            }
        });
    }

    @Override
    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    protected void onPause() {
        super.onPause();
        if(parent != null){
            parent.onPause();
        }
    }

    @Override
    @OnLifecycleEvent(value = Lifecycle.Event.ON_RESUME)
    protected void onResume() {
        super.onResume();
        if(parent != null){
            parent.onResume();
        }
    }

    private void startLifecycle(){
        if(mActivity instanceof LifecycleOwner){
            ((LifecycleOwner)mActivity).getLifecycle().addObserver(this);
        }
    }
}
