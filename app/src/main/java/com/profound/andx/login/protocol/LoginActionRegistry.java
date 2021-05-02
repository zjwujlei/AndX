package com.profound.andx.login.protocol;

import com.profound.andx.login.User;
import com.profound.andx.login.logic.LoginLogicContext;
import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.store.AndXAction;
import com.profound.libandx.store.AndXActionRegistry;

public class LoginActionRegistry extends AndXActionRegistry {
    private LoginLogicContext mContext;
    private LoginBinder mBinder;

    User user;
    public LoginActionRegistry(LoginLogicContext context){
        this.mContext = context;
    }

    public void setBinder(LoginBinder binder){
        this.mBinder = binder;
    }

    public final int MUTATION_REQUEST_DATA = addAction(new AndXAction() {
        @Override
        public void action(Object... args) {
            String name = mContext.getAndXValue(mContext.NAME,String.class);
            String password = mContext.getAndXValue(mContext.NAME,String.class);

            //模拟请求

            //请求成后更新状态
            user = new User("王一","123456");
//            mContext.putState(mContext.IMAGE_SRC,R.string.app_name);
            mContext.putState(mContext.USER,user);
        }
    });

    public final int MUTATION_PLANE_TYPE = addAction(new AndXAction() {
        @Override
        public void action(Object... args) {
//            mContext.putState(mContext.IMAGE_SRC,R.string.app_name);
            user.setName("王二");
            mContext.updateState(mContext.USER);
            mContext.putState(mContext.PLANE_TYPE,args[0]);
        }
    });

    @Override
    protected AndXContextWrapper getCurrent() {
        return mContext;
    }
}
