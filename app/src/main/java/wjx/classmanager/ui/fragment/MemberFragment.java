package wjx.classmanager.ui.fragment;

import android.view.View;
import android.widget.TextView;

import wjx.classmanager.R;
import wjx.classmanager.presenter.impl.MemberPresenterImpl;
import wjx.classmanager.view.MemberView;
import wjx.classmanager.widget.SlideBar;

/**
 * Created by wjx on 2017/9/16.
 */

public class MemberFragment extends BaseFragment implements MemberView,SlideBar.OnSlideBarChangeListener {

    private SlideBar mSlideBar;
    private TextView mMemberSelectText;
    private MemberPresenterImpl mMemberPresenter;

    @Override
    protected void initListener() {
        mSlideBar.setOnSlidingBarChangeListener(this);
    }

    @Override
    protected void initView() {
        mMemberPresenter = new MemberPresenterImpl(this);
        mSlideBar = (SlideBar) mView.findViewById(R.id.slide_bar);
        mMemberSelectText= (TextView) mView.findViewById(R.id.member_select);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_member;
    }

    @Override
    public void onSectionChange(int index, String section) {
        mMemberSelectText.setVisibility(View.VISIBLE);
        mMemberSelectText.setText(section);
    }

    @Override
    public void onSlidingFinish() {
        mMemberSelectText.setVisibility(View.GONE);
    }
}
