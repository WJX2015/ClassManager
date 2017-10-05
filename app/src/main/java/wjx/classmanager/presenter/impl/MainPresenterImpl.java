package wjx.classmanager.presenter.impl;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import cn.bmob.v3.BmobUser;
import wjx.classmanager.presenter.MainPresenter;
import wjx.classmanager.view.MainView;

/**
 * Created by wjx on 2017/10/5.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView mainView){
        mMainView=mainView;
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
        unsignBmob();
        unsignHuanxin();
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
}
