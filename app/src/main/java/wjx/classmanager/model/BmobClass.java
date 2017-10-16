package wjx.classmanager.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by wjx on 2017/10/15.
 */

public class BmobClass extends BmobObject{

    private String username;
    private String classname;
    private String groupid;
    private String description;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
