package com.profound.andx.login;

import com.profound.andx.login.logic.LoginLogicContext;

import org.junit.Assert;
import org.junit.Test;

public class LoginUnitTest {

    @Test
    public void checkPlaneType() throws Exception {
        LoginLogicContext  logic = new LoginLogicContext();
            logic.putState(logic.NAME,"nick");
                logic.putState(logic.PASSWORD,"123456");
                logic.putState(logic.PLANE_TYPE,0);
                logic.commitCompute();
            System.out.println(logic.getAndXValue(logic.LOGIN_INFO).toString());
        Assert.assertEquals(logic.getAndXValue(logic.LOGIN_INFO).toString(),"");
    }

}
