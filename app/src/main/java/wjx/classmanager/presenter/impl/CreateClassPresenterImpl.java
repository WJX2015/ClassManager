package wjx.classmanager.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import wjx.classmanager.model.BmobClass;
import wjx.classmanager.presenter.CreateClassPresenter;
import wjx.classmanager.utils.ThreadUtil;
import wjx.classmanager.view.CreateClassView;

import static wjx.classmanager.model.Constant.MyClass.CLASS_GROUP_ID;

/**
 * Created by wjx on 2017/9/30.
 */

public class CreateClassPresenterImpl implements CreateClassPresenter {

    private CreateClassView mCreateClassView;
    protected List<EMGroup> grouplist = new ArrayList<>();

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

                    //在环信创建群组
                    EMClient.getInstance().groupManager().createGroup(className, classDesc, members, reason, option);
                    //在Bmob创建班级表
                    createTableInBmob(className,classDesc, BmobUser.getCurrentUser());
                } catch (HyphenateException e) {
                    //通知view创建班级失败
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCreateClassView.onCreateFailed("创建群组失败");
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取环信群组的ID
     * @param classname
     * @return
     */
    private String huanXinGroupId(String classname){
        try {
            grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        grouplist = EMClient.getInstance().groupManager().getAllGroups();

        for (EMGroup group:grouplist){
            if (group.getGroupName().equals(classname)){
                return group.getGroupId();
            }
        }
        return "";
    }

    private void createTableInBmob(String classname,String classdesc,BmobUser user) {
        final String groupId=huanXinGroupId(classname);
        BmobClass bmobClass = new BmobClass();
        bmobClass.setClassname(classname);
        bmobClass.setDescription(classdesc);
        bmobClass.setUser(BmobUser.getCurrentUser());
        bmobClass.setGroupid(groupId);
        bmobClass.save(new SaveListener<String>() {
            @Override
            public void done(final String objectId, BmobException e) {
                if(e==null){
                    //通知view创建完成
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e( "run: ", "Bomb创建班级表成功");
                            mCreateClassView.sendBroadcast();
                            mCreateClassView.onCreateSuccess(groupId,objectId);
                        }
                    });
                }else{
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCreateClassView.onCreateFailed("Bomb创建班级表失败");
                        }
                    });
                }
            }
        });
    }
}
