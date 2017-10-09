package wjx.classmanager.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.R;
import wjx.classmanager.model.Manage;
import wjx.classmanager.presenter.ManagePresenter;
import wjx.classmanager.view.ManageView;

/**
 * Created by wjx on 2017/10/9.
 */

public class ManagePresenterImpl implements ManagePresenter {

    private String[] mFunctionTitle={"发布通知","公布成绩","资料收集","活动投票",
            "推优评优","班级管理","班级考勤","更多管理"};

    private int[] mFunctionIcon={R.drawable.manage_publish,R.drawable.manage_score,
            R.drawable.manage_collect,R.drawable.manage_vote,R.drawable.manage_evaluate,
            R.drawable.manage_class,R.drawable.manage_work,R.drawable.manage_more
    };

    private ManageView mManageView;
    private List<Manage> mManages= new ArrayList<>();

    public ManagePresenterImpl(ManageView manageView){
        mManageView =manageView;
        initData();
    }

    private void initData() {
        for(int i=0;i<mFunctionTitle.length;i++){
            mManages.add(new Manage(mFunctionIcon[i],mFunctionTitle[i]));
        }
    }

    public List<Manage> getManageList(){
        return mManages;
    }

}
