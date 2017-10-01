package wjx.classmanager.activity;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import wjx.classmanager.R;
import wjx.classmanager.presenter.impl.RegisterPresenterImpl;
import wjx.classmanager.utils.ThreadUtil;
import wjx.classmanager.view.RegisterView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, RegisterView {

    private EditText mName;
    private EditText mPass;
    private EditText mPhone;    //手机号
    private EditText mCode;     //验证码
    private Button mGetCode;    //获取验证码
    private Button mSign;       //登录

    private TextInputLayout mInputPhone;
    private TextInputLayout mInputCode;

    private RegisterPresenterImpl mRegisterPresenter;


    @Override
    public void initView() {
        mName = (EditText) findViewById(R.id.register_name);
        mPass = (EditText) findViewById(R.id.register_pass);
        mPhone = (EditText) findViewById(R.id.register_phone);
        mCode = (EditText) findViewById(R.id.register_code);

        mGetCode = (Button) findViewById(R.id.register_getcode);
        mSign = (Button) findViewById(R.id.register_sign);

        mInputPhone = (TextInputLayout) findViewById(R.id.register_input_phone);
        mInputCode = (TextInputLayout) findViewById(R.id.register_input_code);

        mRegisterPresenter = new RegisterPresenterImpl(this);

        //禁止输入空格
        mRegisterPresenter.setEditTextInhibitInputSpace(mName);
        mRegisterPresenter.setEditTextInhibitInputSpace(mPass);
        mRegisterPresenter.setEditTextInhibitInputSpace(mPhone);
        mRegisterPresenter.setEditTextInhibitInputSpace(mCode);
    }

    @Override
    public void initListener() {
        mGetCode.setOnClickListener(this);
        mSign.setOnClickListener(this);

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String number = s.toString().trim();
                mInputPhone.setError(mRegisterPresenter.getPhoneErrorText(number));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String code = s.toString().trim();
                mInputCode.setError(mRegisterPresenter.getCodeErrorText(code));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View v) {

        String phone = mPhone.getText().toString();
        String code = mGetCode.getText().toString();
        String name = mName.getText().toString();
        String pwd = mPass.getText().toString();

        switch (v.getId()) {
            case R.id.register_getcode:
                mRegisterPresenter.getCode(phone);
                break;
            case R.id.register_sign:
                mRegisterPresenter.signIn(name,pwd,phone, code);
                break;
        }
    }

    @Override
    public void onGetCodeSleep() {
        TimeCount timeCount = new TimeCount(60000, 1000);
        timeCount.start();
    }

    @Override
    public void showDialog() {
        showProgress("注册中...");
    }

    @Override
    public void hideDialog() {
        hideProgress();
    }

    @Override
    public void makeToast(String string) {
        showToast(string);
    }

    @Override
    public void onRegisterSuccess() {
        Log.e( "onRegisterSuccess: ","注册成功" );
        startActivity(LogInActivity.class);

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    @Override
    public void onRegisterFailed(String message) {
        Log.e( "onRegisterFailed: ","注册失败"+message );
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mGetCode.setBackgroundColor(Color.parseColor("#50000000"));
            mGetCode.setClickable(false);
            mGetCode.setText("(" + millisUntilFinished / 1000 + ") 秒");
        }

        @Override
        public void onFinish() {
            mGetCode.setText("获取验证码");
            mGetCode.setClickable(true);
            mGetCode.setBackgroundColor(Color.parseColor("#ffff8800"));
        }
    }
}
