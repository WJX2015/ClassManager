package wjx.classmanager.presenter.impl;

import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import wjx.classmanager.presenter.LoginPrestener;
import wjx.classmanager.utils.StringUtil;
import wjx.classmanager.view.LogInView;

/**
 * Created by wjx on 2017/9/26.
 */

public class LoginPresenterImpl implements LoginPrestener {

    private LogInView mLogInView;

    public LoginPresenterImpl(LogInView logInView){
        mLogInView =logInView;
    }

    @Override
    public void login(String username, String password) {
        mLogInView.onStartLogin();

        if(StringUtil.checkUserName(username)){
            if(StringUtil.checkPassword(password)){
                loginBmob(username,password);
            }else{
                mLogInView.onPasswordError("密码格式有误");
            }
        }else{
            mLogInView.onUsernameError("用户名格式有误");
        }
    }

    /**
     * 登录Bmob
     * @param name
     * @param password
     */
    private void loginBmob(final String name, final String password){
        BmobUser user = new BmobUser();
        user.setUsername(name);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                   loginHuanXin(name,password);
                }else{
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 登录环信
     * @param name
     * @param password
     */
    private void loginHuanXin(String name, String password) {
        EMClient.getInstance().login(name,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
                mLogInView.onLoginSuccess();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
                mLogInView.onLoginFailed();
            }
        });
    }
}
