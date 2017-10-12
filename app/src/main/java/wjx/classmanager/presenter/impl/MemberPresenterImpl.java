package wjx.classmanager.presenter.impl;

import wjx.classmanager.presenter.MemberPresenter;
import wjx.classmanager.view.MemberView;

/**
 * Created by wjx on 2017/10/9.
 */

public class MemberPresenterImpl implements MemberPresenter {

    private MemberView mMemberView;

    public MemberPresenterImpl(MemberView memberView){
        mMemberView = memberView;
    }
}
