package wjx.classmanager.presenter.impl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupInfo;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.JoinClass;
import wjx.classmanager.presenter.JoinClassPresenter;
import wjx.classmanager.utils.ThreadUtil;
import wjx.classmanager.view.JoinClassView;

/**
 * Created by wjx on 2017/10/14.
 */

public class JoinClassPresenterImpl implements JoinClassPresenter {

    private List<JoinClass> mJoinClasses = new ArrayList<>();
    private JoinClassView mJoinClassView;

    public JoinClassPresenterImpl(JoinClassView joinClassView){
        mJoinClassView=joinClassView;
        initData();
    }

    private void initData() {
        for(int i=0;i<20;i++){
            JoinClass join = new JoinClass("班级"+i);
            mJoinClasses.add(join);
        }
    }

    @Override
    public void searchClassFromServer(String s) {
        mJoinClassView.onStartSearch();
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
//                EMCursorResult<EMGroupInfo> result = EMClient.getInstance().groupManager().getPublicGroupsFromServer(pageSize, cursor);//需异步处理
//                List<EMGroupInfo> groupsList = List<EMGroupInfo> returnGroups = result.getData();
//                String cursor = result.getCursor();
            }
        });
    }

    public List<JoinClass> getJoinClassList(){
        return mJoinClasses;
    }
}
