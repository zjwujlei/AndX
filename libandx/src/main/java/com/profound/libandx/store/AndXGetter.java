package com.profound.libandx.store;

/**
 * Created by wujinglei on 2018/11/20.
 */

public interface AndXGetter<T> {
    void get(String key,T value);
}
