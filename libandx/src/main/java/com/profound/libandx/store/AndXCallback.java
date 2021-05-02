package com.profound.libandx.store;

/**
 * Created by wujinglei on 2018/11/20.
 *
 * Callback for AndX Action. where mutation a action in AndXContext,callback well be invoked.
 *
 */

public interface AndXCallback<T> {

    void callback(T value);

}
