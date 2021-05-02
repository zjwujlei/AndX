package com.profound.andx.login.logic;

import com.profound.andx.R;
import com.profound.andx.login.User;
import com.profound.andx.utils.StringUtils;
import com.profound.libandx.AndXContextWrapper;
import com.profound.libandx.adapter.style.ViewStyle;
import com.profound.libandx.adapter.style.ViewStylePool;
import com.profound.libandx.store.AndXForm;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginLogicContext extends AndXContextWrapper {

    public final int PLANE_TYPE = sInit(0);
    public final int NAME = sInit("");
    public final int PASSWORD = sInit( "");
    public final int PHONE = sInit( "");
    public final int CODE =  sInit( "");
    public final int USER =  sInit(new User("",""));

    public final int LOGIN_INFO = cInitStrict(new AndXForm<String>() {
        @Override
        public String equation() {
            int type = getState(PLANE_TYPE);
            JSONObject obj = new JSONObject();
            try {
                if(type == 0){
                    obj.put("name", getState(NAME))
                            .put("pwd", getState(PASSWORD));
                }else{
                    obj.put("phone", getState(PHONE))
                            .put("code", getState(CODE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return obj.toString();
        }
    });

    public final int LOGIN_ENABLE = cInitStrict(new AndXForm<Boolean>() {
        @Override
        public Boolean equation() {
            int type = getState(PLANE_TYPE);

            if(type == 0){
                return StringUtils.isNotNull(getState(NAME)) &&
                        StringUtils.isNotNull(getState(PASSWORD));
            }else{
                return StringUtils.isNotNull(getState(PHONE)) &&
                        StringUtils.isNotNull(getState(CODE));
            }


        }
    });

    public final int PWD_STYLE = cInit(new AndXForm<ViewStyle>() {
        @Override
        public ViewStyle equation() {
            ViewStyle style = ViewStylePool.getInstance().getStyle();
            if(getIntState(PLANE_TYPE) == 0){
                style.textColor = R.color.gh_cm_text_color_1;
            }else{
                style.textColor = R.color.gh_cm_text_color_4;
            }

            return style;
        }
    });

    public final int MSG_STYLE = cInit(new AndXForm<ViewStyle>() {
        @Override
        public ViewStyle equation() {
            ViewStyle style = ViewStylePool.getInstance().getStyle();
            if(getIntState(PLANE_TYPE) == 1){
                style.textColor = R.color.gh_cm_text_color_1;
            }else{
                style.textColor = R.color.gh_cm_text_color_4;
            }

            return style;
        }
    });

}
