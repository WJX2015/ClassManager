package wjx.classmanager.utils;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * 作者：国富小哥
 * 日期：2017/10/14
 * Created by Administrator
 */

public class GroupUtil {
    //群组列表
    public static List<EMGroup> grouplist;

    public static boolean isHaveGroup(){
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                //先获取群组列表
                try {
                    EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    grouplist = EMClient.getInstance().groupManager().getAllGroups();

                    //目前还没有班级

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });

        if (grouplist==null){
            return false;
        }else {
            return true;
        }

    }

}
