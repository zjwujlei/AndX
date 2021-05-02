package com.profound.andx.login;

import android.animation.AnimatorSet;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.profound.andx.BaseActivity;
import com.profound.andx.R;
import com.profound.andx.listview.ListLogicContext;
import com.profound.andx.login.logic.LoginLogicContext;
import com.profound.andx.login.protocol.LoginActionRegistry;
import com.profound.andx.login.protocol.LoginBinder;
import com.profound.andx.utils.CoverUtils;
import com.profound.andx.utils.DisplayUtils;

/**
 * 登录页面
 * Created by yingjp on 2017/7/21.
 */
public class LoginRealizeActivity extends BaseActivity implements View.OnClickListener {

    private LoginBinder mBinder;
    private LoginActionRegistry mRegistry;

    public View mLayout;
    public ImageView mBackIv;
    public TextView mModeChangeToPasswordTv;
    public TextView mModeChangeToVerificationTv;

    public RelativeLayout mInputRLayout;
    public LinearLayout mModePasswordLLayout;
    /**
     * 用户名登录
     */
    public boolean isFirstDeleteForUserName; //第一次登陆，若有账号，删除时一次性删除
    public EditText mModePasswordUserNameEt;
    public ImageView mModePasswordUserNameCleanIv;
    public View mModePasswordUserNameUnline;
    public EditText mModePasswordPswEt;
    public ImageView mModePasswordPswCleanIv;
    public ImageView mModePasswordPswShowIv;
    public View mModePasswordPswUnline;

    public LinearLayout mModeVerificationLLayout;
    /**
     * 验证码登录
     */
    public boolean isFirstDeleteForCode;   //第一次登陆，若有账号，删除时一次性删除
    public EditText mModeVerificationPhoneEt;
    public ImageView mModeVerificationPhoneCleanIv;
    public View mModeVerificationPhoneUnline;
    public EditText mModeVerificationCodeEt;
    public ImageView mModeVerificationCodeCleanIv;
    public TextView mModeVerificationCodeGetTv;
    public View mModeVerificationCodeUnline;

    public LinearLayout mLoginLLayout;
    public TextView mLoginTv;
    public ImageView mLoginAnimationIv;
    public TextView mPasswordResetTv;

    private String params;

    private float mScreenWidth = 0f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gh_base_activity_login_realize);
        initAndX();
        initAdapter();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        //模拟前一页面传入的入参。
        params = "params";
        mRegistry.commit(mRegistry.MUTATION_REQUEST_DATA,params);

        init();

    }

    private void initAndX(){
        LoginLogicContext logic = new LoginLogicContext()
                .attachActivity(this)
                .finish();
        mBinder = new LoginBinder(logic);
        mRegistry = new LoginActionRegistry(logic);
        mRegistry.setBinder(mBinder);
    }

    private void initAdapter(){
        mLayout = findViewById(R.id.gh_base_login_realize_layout);
        mBackIv = findViewById(R.id.gh_base_login_realize_back);
        mModeChangeToPasswordTv = findViewById(R.id.gh_base_login_realize_mode_changeto_password);
        mModeChangeToVerificationTv = findViewById(R.id.gh_base_login_realize_mode_changeto_verification);

        mInputRLayout = findViewById(R.id.gh_base_login_realize_input_layout);
        mModePasswordLLayout = findViewById(R.id.gh_base_login_realize_mode_password);
        mModePasswordUserNameEt = findViewById(R.id.gh_base_login_realize_mode_password_username);
        mModePasswordUserNameCleanIv = findViewById(R.id.gh_base_login_realize_mode_password_username_clean);
        mModePasswordUserNameUnline = findViewById(R.id.gh_base_login_realize_mode_password_username_unline);
        mModePasswordPswEt = findViewById(R.id.gh_base_login_realize_mode_password_psw);
        mModePasswordPswCleanIv = findViewById(R.id.gh_base_login_realize_mode_password_psw_clean);
        mModePasswordPswShowIv = findViewById(R.id.gh_base_login_realize_mode_password_psw_show);
        mModePasswordPswUnline = findViewById(R.id.gh_base_login_realize_mode_password_psw_unline);

        mModeVerificationLLayout = findViewById(R.id.gh_base_login_realize_mode_verification);
        mModeVerificationPhoneEt = findViewById(R.id.gh_base_login_realize_mode_verification_phone);
        mModeVerificationPhoneCleanIv = findViewById(R.id.gh_base_login_realize_mode_verification_phone_clean);
        mModeVerificationPhoneUnline = findViewById(R.id.gh_base_login_realize_mode_verification_phone_unline);
        mModeVerificationCodeEt = findViewById(R.id.gh_base_login_realize_mode_verification_code);
        mModeVerificationCodeCleanIv = findViewById(R.id.gh_base_login_realize_mode_verification_code_clean);
        mModeVerificationCodeGetTv = findViewById(R.id.gh_base_login_realize_mode_verification_code_get);
        mModeVerificationCodeUnline = findViewById(R.id.gh_base_login_realize_mode_verification_code_unline);

        mLoginLLayout = findViewById(R.id.gh_base_login_realize_login_layout);
        mLoginTv = findViewById(R.id.gh_base_login_realize_login);
        mLoginAnimationIv = findViewById(R.id.gh_base_login_realize_login_animation);
        mPasswordResetTv = findViewById(R.id.gh_base_login_realize_password_reset);

        mBinder.bindView(this);
    }

    private void init(){

        mScreenWidth = DisplayUtils.getDisplayWidth(this);

        initListener();

        mModeVerificationPhoneEt.clearFocus();
        mModeVerificationCodeEt.clearFocus();

        EditText focusView = mModePasswordUserNameEt;
        View unLineView = mModePasswordUserNameUnline;
        String username = "15888888888";
        if (!TextUtils.isEmpty(username)) {
            isFirstDeleteForUserName = true;

            mModePasswordUserNameEt.setText(CoverUtils.accountCover(username));
            mModePasswordUserNameEt.setTag(username);
            mModePasswordUserNameEt.clearFocus();
            focusView = mModePasswordPswEt;
            unLineView = mModePasswordPswUnline;

        } else {
            mModePasswordPswEt.clearFocus();
        }

        startInAnimation(focusView, unLineView);
    }

    private void initListener() {
        mLayout.setOnClickListener(this);
        mBackIv.setOnClickListener(this);
        mModeChangeToPasswordTv.setOnClickListener(this);
        mModeChangeToVerificationTv.setOnClickListener(this);
        mLoginTv.setOnClickListener(this);
        mPasswordResetTv.setOnClickListener(this);

        LoginInputViewUtils.inputViewBindListener(mModePasswordUserNameEt,
                mModePasswordUserNameCleanIv, mModePasswordUserNameUnline);
        LoginInputViewUtils.inputViewBindListener(mModePasswordPswEt,
                mModePasswordPswCleanIv, mModePasswordPswUnline);
        LoginInputViewUtils.inputViewBindListener(mModeVerificationPhoneEt,
                mModeVerificationPhoneCleanIv, mModeVerificationPhoneUnline);
        LoginInputViewUtils.inputViewBindListener(mModeVerificationCodeEt,
                mModeVerificationCodeCleanIv, mModeVerificationCodeUnline);
        LoginInputViewUtils.showOrHintPasswordView(mModePasswordPswEt, mModePasswordPswShowIv);

        LoginInputViewUtils.dispatchOnEditorAction(this, mModePasswordPswEt, mInputRLayout);
        LoginInputViewUtils.dispatchOnEditorAction(this, mModeVerificationCodeEt, mInputRLayout);

//        按钮可否点击配置，可用观察者快速实现
//        new LoginCommonButtonEnableMonitor(mLoginPasswordTv)
//                .addTextWatcherView(mModePasswordUserNameEt)
//                .addTextWatcherView(mModePasswordPswEt);
//        new LoginCommonButtonEnableMonitor(mLoginVerificationTv)
//                .addTextWatcherView(mModeVerificationPhoneEt)
//                .addTextWatcherView(mModeVerificationCodeEt);

//        mModePasswordUserNameEt.setOnKeyListener(new SensitiveDeleteListener());
//        mModeVerificationPhoneEt.setOnKeyListener(new SensitiveDeleteListener());
    }

    private void startInAnimation(EditText focusView, View unLineView) {
        AnimatorSet set = LoginAnimationUtils.inAnimTranslateFromRight(mInputRLayout, mScreenWidth,
                LoginAnimationUtils.ANIMATION_TIME_TOGGLE_LEFT_AND_RIGHT);
        LoginInputViewUtils.showKeyBoardAfterAnim(this, focusView, unLineView, set);
        LoginAnimationUtils.inAnimUpTranslateAndAlpha(mLoginLLayout,
                LoginAnimationUtils.ANIMATION_TIME_TOGGLE_LEFT_AND_RIGHT);
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.gh_base_login_realize_layout) {
            LoginInputViewUtils.hideKeyBoard(this, mInputRLayout);
        } else if (id == R.id.gh_base_login_realize_back) {
            finish();
        } else if (id == R.id.gh_base_login_realize_mode_changeto_password) {
            mRegistry.commit(mRegistry.MUTATION_PLANE_TYPE,0);
        } else if (id == R.id.gh_base_login_realize_mode_changeto_verification) {
            mRegistry.commit(mRegistry.MUTATION_PLANE_TYPE,1);
        } else if (id == R.id.gh_base_login_realize_login) {
            //TODO:密码登录

        } else if (id == R.id.gh_base_login_realize_password_reset) {
            //TODO:跳转到忘记密码页面
        }
    }

}
