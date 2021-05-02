package com.profound.libandx.store;

import com.profound.libandx.AndXContext;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.adapter.style.ViewStylePool;
import com.profound.libandx.error.AndXCastException;
import com.profound.libandx.error.AssertError;
import com.profound.libandx.error.AssertInfo;
import com.profound.libandx.scheduler.AndXSchedulers;
import com.profound.libandx.error.StateNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by wujinglei on 2018/11/30.
 */

public class AndXComputeRegistry {

    private final ArrayList<AndXCompute> mCompute = new ArrayList<>();

    /**
     * 计算式对状态变更感知实现。计算式订阅状态变更，在变更后记录在mChanged内。
     * 调用{@link #commit()}函数后，mChanged内的所有计算式重新计算值并发送。
     */
    private final ArrayList<Integer> mChanged = new ArrayList<>();

    private AndXContext mContext;
    public AndXComputeRegistry(AndXContext context){
        this.mContext = context;
    }

    /**
     * add a compute form into registry,and return stateId.
     * if you had added the state before,it only return the last stateId.
     * @param form
     * @return computeId,the index of subject in {@link #mCompute}
     */
    public <T> int addCompute(AndXForm<T> form){
        synchronized (mCompute){
            int nextId = mCompute.size();
            int computeId = AndXContext.COMPUTE_ID_START+nextId;
            AndXCompute<T> compute = new AndXCompute<>(form,computeId,false);
            mCompute.add(compute);
            return computeId;
        }
    }

    /**
     * add a compute form into registry,and return stateId.
     * if you had added the state before,it only return the last stateId.
     * @param form
     * @param useStrict
     * @return computeId,the index of subject in {@link #mCompute}
     */
    public <T> int addCompute(AndXForm<T> form,boolean useStrict){
        synchronized (mCompute){
            int nextId = mCompute.size();
            int computeId = AndXContext.COMPUTE_ID_START+nextId;
            AndXCompute<T> compute = new AndXCompute<>(form,computeId,useStrict);
            mCompute.add(compute);
            return computeId;
        }
    }

    /**
     *
     *
     */
    public void commit(){
        synchronized (mChanged){
            for(int computeId:mChanged){
                mCompute.get(computeId-AndXContext.COMPUTE_ID_START).commitChange();
            }
            mChanged.clear();
        }
    }


    public <T> boolean subscribe(int computeId, Consumer<T> onNext){
        try {
            AndXCompute<T> subject = obtain(computeId);
            return subject.subscribeCompute(onNext);
        } catch (AndXCastException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * obtain AndXCompute by computeId.
     * if stateId {@code >} {@link #mCompute}.size(),
     * throw {@link StateNotFoundException}.
     *
     * @param computeId compute form id.
     * @param <T>
     * @return
     */
    private <T> AndXCompute<T> obtain(int computeId) throws AndXCastException {
        AssertError.AssertComputeIdOutOfBound(computeId,AndXContext.COMPUTE_ID_START);
        AssertError.AssertComputeValid(computeId-AndXContext.COMPUTE_ID_START, mCompute.size());
        try{
            AndXCompute<T> compute = (AndXCompute<T>)mCompute.get(computeId-AndXContext.COMPUTE_ID_START);
            return compute;
        }catch (Throwable e){
            throw new AndXCastException("obtain compute failed!type error!");
        }
    }

    public <T> T getValue(int computeId){
        AssertError.AssertComputeIdOutOfBound(computeId,AndXContext.COMPUTE_ID_START);
        AndXCompute<T> compute = mCompute.get(computeId-AndXContext.COMPUTE_ID_START);
        return compute.getValue();
    }

    public <T> Observable<T> getObservable(int computeId,Class<T> clazz){
        AssertError.AssertComputeIdOutOfBound(computeId,AndXContext.COMPUTE_ID_START);
        AndXCompute<T> subject = mCompute.get(computeId-AndXContext.COMPUTE_ID_START);
        return subject.getObservable(clazz);
    }

    /**
     *
     * @param <R>
     */
    private class AndXCompute<R> {

        private AndXForm<R> mEquation;
        private PublishSubject<R> mSubject = PublishSubject.create();
        private R mValue;
        private int mComputeId;
        private String mMark;
        private boolean mUseStrict;
        private List<Integer> dependencies = new ArrayList<>();
        private Disposable mDisposable;
        private Observable<R> computeObservable;


        public AndXCompute(AndXForm<R> equation,int computeId,boolean useStrict){
            this.mEquation = equation;
            this.mComputeId = computeId;
            this.mUseStrict = useStrict;
            mContext.startEquation(computeId);
            mValue = mEquation.equation();
            dependencies = mContext.endEquation(computeId);
            mMark = generateMark();
            bindState();
            AssertInfo.assertComputeInfo(mComputeId,mUseStrict,mMark,mValue);
        }

        /**
         * 创建计算式 值发送的Observable。若值为null，不进行发送。
         * @return
         */
        private Observable<R> computeObservable() {
            if(computeObservable == null){
                computeObservable = Observable.just(mEquation)
                        .subscribeOn(AndXSchedulers.onComputeThread())
                        .map(new Function<AndXForm<R>, R>() {
                            @Override
                            public R apply(AndXForm<R> rAndXForm) throws Exception {
                                if (mUseStrict) {
                                    mContext.startEquation(mComputeId);
                                }

                                if(mValue instanceof ViewStyle){
                                    ViewStylePool.getInstance().recycle((ViewStyle) mValue);
                                }

                                mValue = rAndXForm.equation();
                                if (mUseStrict) {
                                    dependencies = mContext.endEquation(mComputeId);
                                    String mark = generateMark();
                                    if (!mark.equals(mMark)) {
                                        mMark = mark;
                                        if (mDisposable != null) {
                                            mDisposable.dispose();
                                        }
                                    }
                                    bindState();
                                }
                                return mValue;
                            }
                        }).filter(new Predicate<R>() {
                            @Override
                            public boolean test(R r) throws Exception {
                                return mValue != null;
                            }
                        });
            }
            return computeObservable;
        }

        private String generateMark(){
            StringBuffer markBuffer = new StringBuffer();
            markBuffer.append(dependencies.size());
            Collections.sort(dependencies);
            for(Integer i:dependencies){
                markBuffer.append("_");
                markBuffer.append(i);
            }
            return markBuffer.toString();
        }

        private void bindState(){
            if(dependencies.size() == 1){
                bindSource(mContext.getStateObservable(dependencies.get(0)));
            }else if(dependencies.size() == 2){
                bindSource(
                        mContext.getStateObservable(dependencies.get(0)),
                        mContext.getStateObservable(dependencies.get(1)));
            }else if(dependencies.size() == 3){
                bindSource(
                        mContext.getStateObservable(dependencies.get(0)),
                        mContext.getStateObservable(dependencies.get(1)),
                        mContext.getStateObservable(dependencies.get(2)));
            }else if(dependencies.size() == 4){
                bindSource(
                        mContext.getStateObservable(dependencies.get(0)),
                        mContext.getStateObservable(dependencies.get(1)),
                        mContext.getStateObservable(dependencies.get(2)),
                        mContext.getStateObservable(dependencies.get(3)));
            }else {
                bindSource(dependencies);
            }
        }

        public R getValue(){
            return mValue;
        }

        public Observer<Boolean> getObserver(){
            return new Observer<Boolean>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mDisposable = d;
                }

                @Override
                public void onNext(Boolean value) {
                    if(value){
                        onValueChange();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
        }

        protected void onValueChange(){
            synchronized (mChanged){
                if(!mChanged.contains(mComputeId)){
                    mChanged.add(mComputeId);
                }
            }
        }

        /**
         * onNext预约当前计算式的变更。
         * 粘性实现，当{@link #mValue}不为null时
         * @param onNext
         * @return
         */
        public boolean subscribeCompute(Consumer<R> onNext){
            try {
                onNext.accept(mValue);
                mSubject.subscribe(onNext);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void commitChange(){
            computeObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<R>() {
                        @Override
                        public void accept(R r) throws Exception {
                            mSubject.onNext(r);
                        }
                    });
        }

        public Observable<R> getObservable(){
            return mSubject;
        }

        private Observable<R> getObservable(Class<R> clazz){
            return mSubject.ofType(clazz);
        }


        private <T> Observable<Boolean> packageChange(Observable<T> observable){
            return observable.map(new Function<T, Boolean>() {
                @Override
                public Boolean apply(T t) throws Exception {
                    return true;
                }
            });
        }

        private <T> void bindSource(Observable<T> subject){
            Observable<Boolean> observable = packageChange(subject);
            Observer<Boolean> observer = getObserver();
            observable.subscribe(observer);
        }

        private <T1,T2> void bindSource(Observable<T1> subject1,
                                       Observable<T2> subject2){

            Observable<Boolean> observable1 = packageChange(subject1);
            Observable<Boolean> observable2 = packageChange(subject2);

            Observable<Boolean> observable = Observable.merge(observable1,observable2);
            Observer<Boolean> observer = getObserver();
            observable.subscribe(observer);
        }

        private <T1,T2,T3> void bindSource(Observable<T1> subject1,
                                          Observable<T2> subject2,
                                          Observable<T3> subject3){

            Observable<Boolean> observable1 = packageChange(subject1);
            Observable<Boolean> observable2 = packageChange(subject2);
            Observable<Boolean> observable3 = packageChange(subject3);

            Observer<Boolean> observer = getObserver();

            Observable<Boolean> observable = Observable.merge(observable1,observable2,observable3);
            observable.subscribe(observer);
        }

        private <T1,T2,T3,T4> void bindSource(Observable<T1> subject1,
                                             Observable<T2> subject2,
                                             Observable<T3> subject3,
                                             Observable<T4> subject4){

            Observable<Boolean> observable1 = packageChange(subject1);
            Observable<Boolean> observable2 = packageChange(subject2);
            Observable<Boolean> observable3 = packageChange(subject3);
            Observable<Boolean> observable4 = packageChange(subject4);

            Observer<Boolean> observer = getObserver();

            Observable<Boolean> observable = Observable.merge(observable1,observable2,observable3,observable4);
            observable.subscribe(observer);
        }

        private void bindSource(List<Integer>stateIds){
            List<Observable<Boolean>> list = new ArrayList<>();
            for(int stateId:stateIds){
                AssertError.AssertStateIdOutOfBound(stateId,AndXContext.COMPUTE_ID_START);
                list.add(packageChange(mContext.getStateObservable(stateId)));
            }
            Observer<Boolean> observer = getObserver();
            Observable<Boolean> observable = Observable.merge(list);
            observable.subscribe(observer);
        }
    }

}
