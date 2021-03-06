package wjx.classmanager.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import wjx.classmanager.R;
import wjx.classmanager.application.MyApplication;
import wjx.classmanager.collector.ActivityCollector;
import wjx.classmanager.model.Constant;
import wjx.classmanager.model.Message;

import static java.security.AccessController.getContext;
import static wjx.classmanager.model.Constant.MessageType.CREATE_CLASS;

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
        immersive(isImmersive());

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

    public abstract boolean isImmersive();

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
            mProgressDialog.setCancelable(true);
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

    private void immersive(boolean request) {
        if (Build.VERSION.SDK_INT >= 21 && request) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 发送消息广播
     * @param message
     */
    public void sendMessageBroadcast(Message message){
        Intent intent = new Intent(Constant.Receiver.ACTION);
        intent.putExtra(Constant.Receiver.RECEIVE,message);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    /**
     * 权限检查方法
     *
     * @param permissions
     * @return
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限申请方法
     *
     * @param code
     * @param permissions
     */
    public void requestPermission(Activity activity,int code, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, code);
    }
}
