package wjx.classmanager.presenter.impl;

import wjx.classmanager.presenter.NotifyPresenter;
import wjx.classmanager.view.NotifyView;

/**
 * Created by wjx on 2017/10/9.
 */

public class NotifyPresenterImpl implements NotifyPresenter {

    private NotifyView mNotifyView;

    public NotifyPresenterImpl(NotifyView notifyView){
        mNotifyView =notifyView;
    }
}
