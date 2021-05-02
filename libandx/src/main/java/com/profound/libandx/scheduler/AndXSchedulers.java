package com.profound.libandx.scheduler;

import java.util.concurrent.Executors;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wujinglei on 2018/12/3.
 */

public class AndXSchedulers {

    /**
     * 计算线程，单个线程实现。
     * @return
     */
    public static Scheduler onComputeThread(){
        return Schedulers.from(Executors.newSingleThreadExecutor());
    }

}
