package wjx.classmanager.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import wjx.classmanager.application.MyApplication;
import wjx.classmanager.collector.ActivityCollector;

import static java.security.AccessController.getContext;

/**
 * Created by wjx on 2017/9/14.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toast mToast;
    protected Context mContext;
    private ProgressDialog mProgressDialog;
    private InputMethodManager mInputMethodManager;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mContext = MyApplication.getMyContext();
        ActivityCollector.addActivity(this);

        initView();
        initListener();
        initData();
    }

    protected void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract int getLayout();

    protected void startActivity(Class activity) {
        startActivity(activity, false);
    }

    protected void startActivity(Class activity, String key, String extra) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtra(key, extra);
        startActivity(intent);
    }

    protected void startActivity(Class activity, boolean finish) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);

            /**
             * Bug Time
             * new ProgressDialog(全局上下文);
             * AlertDialog 只需依赖一个View的上下文
             *
             * show()-----hide()
             * finish当前Activity
             * 窗口泄漏
             */
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void hideKeyBoard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    protected void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    protected void postDelay(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mToast = null;
        mProgressDialog = null;
        ActivityCollector.removeActivity(this);
    }
}
