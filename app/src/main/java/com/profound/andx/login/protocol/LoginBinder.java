package com.profound.andx.login.protocol;


import com.profound.andx.login.LoginRealizeActivity;
import com.profound.andx.login.logic.LoginLogicContext;
import com.profound.libandx.AndXBinder;
import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.store.TransformFunc;

import io.reactivex.functions.Consumer;

public class LoginBinder extends AndXBinder {

    private LoginLogicContext mLogic;

    public LoginBinder(LoginLogicContext logic) {
        this.mLogic = logic;
    }

    @Override
    public AndXContextWrapper getContext() {
        return mLogic;
    }

    public void bindView(LoginRealizeActivity holder) {
        getAdapter(holder.mModeChangeToPasswordTv).bindStyle(mLogic.PWD_STYLE);
        getAdapter(holder.mModeChangeToVerificationTv).bindStyle(mLogic.MSG_STYLE);

        getAdapter(holder.mModePasswordLLayout).bindVisible(mLogic.PLANE_TYPE, new TransformFunc<Integer, Boolean>() {
            @Override
            public Boolean transform(Integer value) {
                return value == 0;
            }
        });
        getAdapter(holder.mModePasswordUserNameEt).bindText(mLogic.NAME);
        getAdapter(holder.mModePasswordPswEt).bindText(mLogic.PASSWORD);
        getAdapter(holder.mModeVerificationLLayout).bindVisible(mLogic.PLANE_TYPE, new TransformFunc<Integer, Boolean>() {
            @Override
            public Boolean transform(Integer value) {
                return value == 1;
            }
        });
        getAdapter(holder.mModeVerificationPhoneEt).bindText(mLogic.NAME);
        getAdapter(holder.mModeVerificationCodeEt).bindText(mLogic.CODE);
        getAdapter(holder.mLoginTv).bindEnable(mLogic.LOGIN_ENABLE);
        bind(mLogic.PLANE_TYPE, new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

            }
        });
    }
}
