package wjx.classmanager.presenter.impl;

import java.util.ArrayList;
import java.util.List;

import wjx.classmanager.model.Member;
import wjx.classmanager.presenter.MemberPresenter;
import wjx.classmanager.view.MemberView;

/**
 * Created by wjx on 2017/10/9.
 */

public class MemberPresenterImpl implements MemberPresenter {

    private MemberView mMemberView;
    private List<Member> mMembers = new ArrayList<>();

    public MemberPresenterImpl(MemberView memberView){
        mMemberView = memberView;
        initData();
    }

    private void initData() {
        for(int i=0;i<20;i++){
            Member member = new Member("wjx");
            member.setSection(true);
            mMembers.add(member);
        }
    }

    public List<Member> getMemberList(){
        return mMembers;
    }
}
