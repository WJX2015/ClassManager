package wjx.classmanager.view;

import wjx.classmanager.widget.ExitDialog;

/**
 * Created by wjx on 2017/10/5.
 */

public interface MainView {

    void logoutSuccess();

    void logoutFailed();

    void onStartLogout();

    void intentMyClass(String groupId);

    void onError(String error);

    void intentPostData(String id);
}
