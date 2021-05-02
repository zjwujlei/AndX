package com.profound.libandx.error;

/**
 * Created by wujinglei on 2018/11/20.
 */

public class AssertError {

    public static void AssertAndXObjectName(String name) throws AndXObjectException {
        if(name == null || name.length()==0){
            throw new AndXObjectException("Names must be non-null");
        }
    }

    public static <T> void AssertStateValid(int id,T t) throws StateNotFoundException {
        if(t == null){
            throw new StateNotFoundException("data by id:"+id+" not found!");
        }
    }

    public static void AssertStateValid(int stateId,int registrySize) throws StateNotFoundException {
        if(stateId >= registrySize){
            throw new StateNotFoundException("can`t found state with id:"+stateId+",registry size is  "+registrySize);
        }
    }

    public static void AssertComputeValid(int stateId,int registrySize) throws StateNotFoundException {
        if(stateId >= registrySize){
            throw new StateNotFoundException("can`t found compute with id:"+stateId+",registry size is  "+registrySize);
        }
    }

    public static void AssertStateIdOutOfBound(int stateId,int maxId) throws StateNotFoundException {
        if(stateId >= maxId || stateId < 0){
            throw new StateOutOfBoundException("stateId out of bound,it should be > 0 and < 0x"+maxId+", ,stateId:"+stateId+", dou you want use compute?");
        }
    }

    public static void AssertComputeIdOutOfBound(int computeId,int minId) throws StateNotFoundException {
        if(computeId < minId){
            throw new StateOutOfBoundException("computeId out of bound,it should be >= "+minId+", ,computeId:"+computeId+", dou you want use state?");
        }
    }

    public static void AssertStateCastException(int stateId,Object found,Class<?> require) throws DataCastException {
        if(!require.isInstance(found)){
            throw new DataCastException("stateId:"+stateId+" state type error,require "+require.getName()+" but find "+found.getClass().getName()+"!");
        }
    }

    public static void AssertComputeCastException(int computeId,Object found,Class<?> require) throws DataCastException {
        if(!require.isInstance(found)){
            throw new DataCastException("computeId:"+computeId+" compute type error,require "+require.getName()+" but find "+found.getClass().getName()+"!");
        }
    }

    public static void AssertWrapperNotFound(int wrapperPref) throws StateNotFoundException {
        throw new StateNotFoundException("not found state! wrapper hex pref :"+Integer.toHexString(wrapperPref));
    }
}
