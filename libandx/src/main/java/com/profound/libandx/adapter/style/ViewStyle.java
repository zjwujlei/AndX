package com.profound.libandx.adapter.style;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.text.InputType;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by wujl on 2018/12/18 下午7:25
 */
public class ViewStyle {

    private static AtomicInteger mAtomic = new AtomicInteger(1);
    private int objectId;

    /*------背景相关样式-----*/
    @ColorRes
    public int backgroundColor;

    @DrawableRes
    public int backgroundImage;

    /*------TextView相关样式------*/
    @ColorRes
    public int textColor;

    public int maxLine;

    /*------EditText相关样式------*/
    public int inputType;


    /*------ImageView相关样式----*/
    public ImageView.ScaleType scaleType;





    public ViewStyle(){
        objectId = mAtomic.getAndAdd(1);
    }


    public void clear(){
        textColor = 0;
        backgroundColor = 0;
        backgroundImage = 0;
        scaleType = null;
    }

    @Override
    public String toString() {
        return "[ViewStyle Object id:"+objectId+"]";
    }
}
