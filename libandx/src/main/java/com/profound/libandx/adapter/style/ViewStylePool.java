package com.profound.libandx.adapter.style;

import java.util.ArrayList;
import java.util.List;

/**
 * create by wujl on 2018/12/18 下午7:43
 */
public class ViewStylePool {
    List<ViewStyle> recycle = new ArrayList<>();

    private static ViewStylePool sInstance;

    private ViewStylePool(){
    }

    public synchronized static ViewStylePool getInstance(){
        if(sInstance == null){
            sInstance = new ViewStylePool();
        }
        return sInstance;
    }

    public ViewStyle getStyle(){
        synchronized (recycle){
            ViewStyle style;
            if(recycle.size() == 0){
                style = new ViewStyle();
            }else{
                style = recycle.get(0);
                recycle.remove(style);
            }
            return style;
        }
    }

    public void recycle(ViewStyle style){
        synchronized (recycle){
            style.clear();
            recycle.add(style);
        }
    }

}
