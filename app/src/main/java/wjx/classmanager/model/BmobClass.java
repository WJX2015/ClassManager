package wjx.classmanager.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by wjx on 2017/10/15.
 */

public class BmobClass extends BmobObject{

    private BmobUser user;
    private String classname;
    private String groupid;
    private String description;
    private BmobPhoto photo;

    public BmobPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(BmobPhoto photo) {
        this.photo = photo;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
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
