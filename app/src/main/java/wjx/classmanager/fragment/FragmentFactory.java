package wjx.classmanager.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by wjx on 2017/10/3.
 */

public class FragmentFactory {

    public static final String MANAGE="manage_fragment";
    public static final String MESSAGE="message_fragment";
    public static final String NOTIFY="notify_fragment";

    /**
     * 获取一个碎片
     * @return
     */
    public static Fragment getFragment(String key){
        switch (key){
            case MANAGE:
                return new ManageFragment();
            case MESSAGE:
                return new MessageFragment();
            case NOTIFY:
                return new NotifyFragment();
        }
        return null;
    }
}
