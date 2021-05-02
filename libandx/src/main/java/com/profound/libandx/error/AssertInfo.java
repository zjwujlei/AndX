package com.profound.libandx.error;

import android.util.Log;

/**
 * Created by wujinglei on 2018/11/23.
 */

public class AssertInfo {

    public static final String TAG = "AssertInfo";

    public static <T> void assertNullState(int id){
        Log.i(TAG,"state with id:"+id+" is null!");
    }

    public static <T> void assertSubscribeNullState(int id){
        Log.i(TAG,"subscribeActual。 state with id:"+id+" is null!");
    }

    public static <T> void assertUpdateState(int id){
        Log.i(TAG,"update a state. id:"+id);
    }

    public static <T> void assertPushState(int id, T value){
        Log.i(TAG,"push a state. id:"+id+" value:"+value);
    }

    public static <T> void assertComputeInfo(int computeId, boolean useStrict, String mark, T t){
        Log.e(TAG,"check compute form info,computeId:"+computeId+
                " useStrict:"+useStrict+" mark:"+mark+" value:"+t);
    }

    public static <T> void assertStartEquation(int computeId){
        try{
            Log.e(TAG,"start to invoke equation! computeId:"+computeId);
        }catch (Exception e){

        }
    }

    public static <T> void assertMarkDependance(int stateId){
        try{
            Log.e(TAG,"mark stateId for compute! stateId:"+stateId);
        }catch (Exception e){

        }
    }

    public static <T> void assertEndEquation(int computeId){
        Log.e(TAG,"end to invoke equation! computeId:"+computeId);
    }


}
