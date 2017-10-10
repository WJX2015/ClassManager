package wjx.classmanager.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wjx.classmanager.R;
import wjx.classmanager.presenter.impl.LoginPresenterImpl;
import wjx.classmanager.utils.SPUtil;
import wjx.classmanager.view.LogInView;

public class LogInActivity extends BaseActivity implements View.OnClickListener,LogInView{

    private EditText mUsername;
    private EditText mPassword;
    private Button mRegister;
    private Button mLogin;

    private LoginPresenterImpl mLoginPresenter;

    @Override
    public void initView() {
        mUsername = (EditText) findViewById(R.id.log_name);
        mPassword = (EditText) findViewById(R.id.log_pass);
        mRegister = (Button) findViewById(R.id.log_register);
        mLogin = (Button) findViewById(R.id.log_in);
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void initListener() {
        mRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_log_in;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.log_in:
                mLoginPresenter.login(mUsername.getText().toString(),mPassword.getText().toString());
                break;
        }
    }

    @Override
    public void onUsernameError(String error) {
        showToast(error);
        hideProgress();
    }

    @Override
    public void onPasswordError(String error) {
        showToast(error);
        hideProgress();
    }

    @Override
    public void onLoginSuccess() {
        hideProgress();
        showToast("登录成功");
        if(isFirstLogin()){
            startActivity(NavigationActivity.class,true);
        }else{
            startActivity(MainActivity.class,true);
        }
    }

    @Override
    public void onLoginFailed() {
        showToast("登录失败");
        hideProgress();
    }

    @Override
    public void onStartLogin() {
        showProgress("登录中...");
    }

    /**
     * 判断用户是否第一次启动应用
     * @return
     */
    public boolean isFirstLogin(){
        if(SPUtil.getFirstRunTag(mContext)){
            SPUtil.addFirstRunTag(mContext,false);
            return true;
        }
        return false;
    }
}
