package wjx.classmanager.presenter.impl;

import android.text.TextUtils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.exceptions.HyphenateException;

import wjx.classmanager.presenter.CreateClassPresenter;
import wjx.classmanager.utils.ThreadUtil;
import wjx.classmanager.view.CreateClassView;

/**
 * Created by wjx on 2017/9/30.
 */

public class CreateClassPresenterImpl implements CreateClassPresenter {

    private CreateClassView mCreateClassView;

    public CreateClassPresenterImpl(CreateClassView createClassView){
        mCreateClassView =createClassView;
    }

    @Override
    public void createClass(String className, String classDesc, boolean isPublic, boolean isMemberInviter) {
        if (TextUtils.isEmpty(className)) {
            mCreateClassView.classNameErroe();
        } else {
            //通知View层正在创建班级
            mCreateClassView.onCreateClass();
            startCreate(className,classDesc,isPublic,isMemberInviter);
        }
    }
    private void startCreate(final String className, final String classDesc, final boolean isPublic, final boolean isMemberInviter) {

        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {

                try {
                    EMGroupOptions option = new EMGroupOptions();
                    option.maxUsers = 200;
                    option.inviteNeedConfirm = true;
                    String[] members=new String[]{};
                    String reason = "Invite to join the class";
                    reason  = EMClient.getInstance().getCurrentUser() + reason + className;

                    if(isPublic){
                        if (isMemberInviter){
                            option.style= EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval;
                        }else {
                            option.style= EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                        }
                    }else{
                        if (isMemberInviter){
                            option.style= EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                        }else {
                            option.style= EMGroupManager.EMGroupStyle.EMGroupStylePrivateOnlyOwnerInvite;
                        }
                    }

                    EMClient.getInstance().groupManager().createGroup(className, classDesc, members, reason, option);

                    //通知view创建完成
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCreateClassView.onCreateSuccess();
                        }
                    });

                } catch (HyphenateException e) {
                    //通知view创建班级失败
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCreateClassView.onCreateFailed();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }
}
