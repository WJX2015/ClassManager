package wjx.classmanager.ui.fragment;

import wjx.classmanager.R;
import wjx.classmanager.presenter.impl.NotifyPresenterImpl;
import wjx.classmanager.view.NotifyView;

/**
 * Created by wjx on 2017/9/16.
 */

public class NotifyFragment extends BaseFragment implements NotifyView{

    private NotifyPresenterImpl mNotifyPresenter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        mNotifyPresenter = new NotifyPresenterImpl(this);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_notify;
    }
}
