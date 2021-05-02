package com.profound.libandx.store;

public interface TransformFunc<I,O> {
    O transform(I value);
}
