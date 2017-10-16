package wjx.classmanager.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import wjx.classmanager.model.BmobClass;
import wjx.classmanager.presenter.MyclassPresenter;
import wjx.classmanager.utils.ThreadUtil;
import wjx.classmanager.view.MyClassView;

/**
 * Created by wjx on 2017/9/20.
 */

public class MyclassPresenterImpl implements MyclassPresenter {

    private MyClassView mMyClassView;

    public MyclassPresenterImpl(MyClassView myClassView){
        mMyClassView =myClassView;
    }

    @Override
    public void disbandClass(final String groupId) {
        mMyClassView.onStartDealWith("正在解散班级...");
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().destroyGroup(groupId);//需异步处理
                    Thread.sleep(2000);
                    mMyClassView.onExitSuccess("解散班级成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    mMyClassView.onFailed(e.toString());
                }
            }
        });
    }

    @Override
    public void exitClass(final String groupId) {
        mMyClassView.onStartDealWith("正在退出班级...");
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().leaveGroup(groupId);//需异步处理
                    Thread.sleep(2000);
                    mMyClassView.onExitSuccess("退出班级成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    mMyClassView.onFailed(e.toString());
                }
            }
        });
    }

    @Override
    public void changeClassDesc(final String groupId) {
        mMyClassView.onStartDealWith("正在修改班级描述...");
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().changeGroupName(groupId,"");
                    changeClassDescInBmob();
                    mMyClassView.onChangeSuccess("修改班级描述成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    mMyClassView.onFailed("修改班级描述失败");
                }
            }
        });
    }

    private void changeClassDescInBmob() {
        BmobClass bombClass = new BmobClass();
        //bombClass.getObjectId().
        bombClass.setDescription("");
        bombClass.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
    }

    @Override
    public void changeClassName(final String groupId) {
        mMyClassView.onStartDealWith("正在修改班级名称...");
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().groupManager().changeGroupDescription(groupId,"");
                    mMyClassView.onChangeSuccess("修改班级名称成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    mMyClassView.onFailed("修改班级名称失败");
                }
            }
        });
    }
}
