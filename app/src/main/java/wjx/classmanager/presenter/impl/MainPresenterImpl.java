package wjx.classmanager.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import cn.bmob.v3.BmobUser;
import wjx.classmanager.R;
import wjx.classmanager.adapter.MessageAdapter;
import wjx.classmanager.presenter.MainPresenter;
import wjx.classmanager.view.MainView;
import wjx.classmanager.widget.ExitDialog;

/**
 * Created by wjx on 2017/10/5.
 */

public class MainPresenterImpl implements MainPresenter,ExitDialog.onPositiveButtonClickListener{

    private MainView mMainView;
    private Context mContext;

    public MainPresenterImpl(MainView mainView,Context context){
        mMainView=mainView;
        mContext =context;
    }

    @Override
    public void myClass() {

    }

    @Override
    public void evaluate() {

    }

    @Override
    public void activityVote() {

    }

    @Override
    public void postData() {

    }

    @Override
    public void personalInfo() {

    }

    @Override
    public void unSign() {
        showAlertDialog();
    }

    private void unsignBmob(){
        BmobUser.logOut();
        Log.e("========","Bmob退出登录成功");
    }

    private void unsignHuanxin(){
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.e("========","环信退出登录成功");
                mMainView.logoutSuccess();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("========","环信退出登录失败");
            }
        });
    }

    protected void showAlertDialog(){
        //dialog不能依赖全局的上下文，需要的是当前activity
        ExitDialog dialog = new ExitDialog(mContext,R.style.exit_dialog);
        dialog.setPositiveButtonClick(this);
        dialog.show();
    }

    @Override
    public void onPositiveButtonClick(ExitDialog exitDialog) {
        unsignBmob();
        unsignHuanxin();
        exitDialog.dismiss();
    }
}
