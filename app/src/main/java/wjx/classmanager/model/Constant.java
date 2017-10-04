package wjx.classmanager.model;

/**
 * Created by wjx on 2017/9/30.
 */

public class Constant { //常量类

    //登录用户已存在
    public static class ErrorCode {
        public static final int USER_ALREADY_EXIST = 202;
    }

    public static class FragmentType {
        //创建Fragment
        public static final String CREATE_MANAGE = "manage_fragment";
        public static final String CREATE_MESSAGE = "message_fragment";
        public static final String CREATE_NOTIFY = "notify_fragment";

        //显示Fragment
        public static final int FRAGMENT_MSG = 0;
        public static final int FRAGMENT_NOTIFY = 1;
        public static final int FRAGMENT_MANAGE = 2;
        public static final int FRAGMENT_COUNT = 3;
    }

    public static class MessageType {
        public static final int JOIN_CLASS = 4;       //加入班级
        public static final int QUIT_CLASS = 5;       //退出班级
        public static final int EVALUATE = 6;         //推优评优
        public static final int ACTIVITY_VOTE = 7;    //活动投票
        public static final int POST_DATA = 8;        //上传资料
        public static final int PERSON_INFO = 9;      //个人信息
        public static final int PHOTO_CLASS = 10;     //班级相册

    }
}
