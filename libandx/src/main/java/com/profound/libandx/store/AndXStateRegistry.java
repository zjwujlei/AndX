package com.profound.libandx.store;


import com.profound.libandx.AndXContext;
import com.profound.libandx.error.AndXCastException;
import com.profound.libandx.error.AssertError;
import com.profound.libandx.error.AssertInfo;
import com.profound.libandx.error.StateNotFoundException;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by wujinglei on 2018/11/29.
 */

public class AndXStateRegistry {

    private ArrayList<AndXState> mStates = new ArrayList<>();

    /**
     *  Life cycle perception.when activity is paused，don`t change the bound view。
     *  add the state into {@link #mChanges}.
     *  waiting for activation,then notify all bound view.
     */
    private ArrayList<AndXState> mChanges = new ArrayList<>();

    /**
     * add a state into registry,and return stateId.
     * if you had added the state before,it only return the last stateId.
     * @param value
     * @return stateId,the index of subject in {@link #mStates}
     */
    public <T> int addState(T value){
        synchronized (mStates){
            int nextId = mStates.size();
            AndXState<T> state = new AndXState<>(nextId,value);
            mStates.add(state);
            return nextId;
        }
    }

    /**
     *
     * @param stateId
     * @param value
     * @param isSilent
     * @param <T>
     */
    public <T> void pushValue(int stateId, T value,boolean isSilent){
        AssertError.AssertStateIdOutOfBound(stateId, AndXContext.COMPUTE_ID_START);
        if(mStates.size()>stateId){
            AndXState<T> subject = mStates.get(stateId);
            if(isSilent){
                synchronized (mChanges){
                    if(!mChanges.contains(subject)){
                        mChanges.add(subject);
                    }
                }
                subject.pushValueSilence(value);
            }else{
                subject.pushValue(value);
            }
        }
    }

    /**
     *
     * @param stateId
     */
    public void updateState(int stateId,boolean isSilent){
        AssertError.AssertStateIdOutOfBound(stateId, AndXContext.COMPUTE_ID_START);
        if(mStates.size()>stateId){
            AndXState subject = mStates.get(stateId);
            if(isSilent){
                synchronized (mChanges){
                    if(!mChanges.contains(subject)){
                        mChanges.add(subject);
                    }
                }
                subject.updateValueSilence();
            }else{
                subject.updateValue();
            }
        }
    }


    public void notifyChanges(){
        synchronized (mChanges){
            for(AndXState state:mChanges){
                state.updateValue();
            }
            mChanges.clear();
        }
    }

    public <T> boolean subscribe(int stateId, Observer<T> observer){
        try{
            AndXState<T> subject = obtain(stateId);
            return subject.subscribeActual(observer);
        }catch (AndXCastException e){
            e.printStackTrace();
            return false;
        }
    }

    public <T> boolean subscribe(int stateId, Consumer<T> onNext){
        try{
            AndXState<T> subject = obtain(stateId);
            return subject.subscribeActual(onNext);
        }catch (AndXCastException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * obtain AndXSubject by stateId.
     * if stateId {@code >} {@link #mStates}.size(),
     * throw {@link StateNotFoundException}.
     *
     * @param stateId state id.
     * @param <T>
     * @return
     */
    private <T> AndXState<T> obtain(int stateId) throws AndXCastException {
        AssertError.AssertStateIdOutOfBound(stateId,AndXContext.COMPUTE_ID_START);
        AssertError.AssertStateValid(stateId,mStates.size());
        try{
            AndXState<T> subject = (AndXState<T>)mStates.get(stateId);
            return subject;
        }catch (Throwable thr){
            throw new AndXCastException("obtain state failed!type error!");
        }

    }

    public <T> T getValue(int stateId){
        AssertError.AssertStateIdOutOfBound(stateId,AndXContext.COMPUTE_ID_START);
        AndXState<T> subject = mStates.get(stateId);
        return subject.getValue();
    }

    public <T> Observable<T> getObservable(int stateId){
        AssertError.AssertStateIdOutOfBound(stateId,AndXContext.COMPUTE_ID_START);
        AndXState<T> subject = mStates.get(stateId);
        return subject.getObservable();
    }

    public <T> Observable<T> getObservable(int stateId, Class<T> clazz){
        AssertError.AssertStateIdOutOfBound(stateId,AndXContext.COMPUTE_ID_START);
        AndXState<T> subject = mStates.get(stateId);
        return subject.getObservable(clazz);
    }

    private class AndXState<T>{
        private T t;
        private int stateId;

        public PublishSubject<T> mSubject = PublishSubject.create();

        public AndXState(int stateId,T defaultValue){
            this.stateId = stateId;
            this.t = defaultValue;
        }
        /**
         * observer预约了当前状态的变更。
         * 粘性实现，如果当前状态值不为null，预约时发送最新的值。
         *
         * @param observer
         * @return
         */

        public boolean subscribeActual(Observer<? super T> observer) {
            try{
                if(t != null){
                    AssertInfo.assertSubscribeNullState(stateId);
                    observer.onNext(t);
                }
                mSubject.subscribeActual(observer);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;

            }
        }

        /**
         * onNext预约了当前状态的变更。
         * 粘性实现，如果当前状态值不为null，预约时发送最新的值。
         *
         * @param onNext
         * @return
         */
        public boolean subscribeActual(Consumer<? super T> onNext) {
            try{
                if(t != null){
                    AssertInfo.assertSubscribeNullState(stateId);
                    onNext.accept(t);
                }
                mSubject.subscribe(onNext);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }


        /**
         * 为当前状态push一个新的值。
         * 如果该值不为null，会发送给所有观察者。
         * @param t
         */
        void pushValue(T t){
            AssertInfo.assertPushState(stateId,t);
            this.t = t;
            if(t != null){
                mSubject.onNext(t);
            }
        }

        /**
         * 静默模式push一个新的值。
         * 不发送给观察者。
         * @param t
         */
        void pushValueSilence(T t){
            AssertInfo.assertPushState(stateId,t);
            this.t = t;
        }

        /**
         * 通知当前状态对象，其值{@link #t}已经变更。
         * 如果该值不为null，会发送给所有观察者。
         */
        void updateValue(){
            AssertInfo.assertUpdateState(stateId);
            if(t!=null){
                mSubject.onNext(t);
            }
        }
        /**
         * 通知当前状态对象，其值{@link #t}已经变更。
         * 不发送给观察者。
         */
        void updateValueSilence(){
            AssertInfo.assertUpdateState(stateId);
        }

        public T getValue(){
            return t;
        }

        Observable<T> getObservable(){
            return mSubject;
        }

        Observable<T> getObservable(Class<T> clazz){
            return mSubject.ofType(clazz);
        }
    }


}
