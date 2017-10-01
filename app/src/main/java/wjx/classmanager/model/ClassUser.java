package wjx.classmanager.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by wjx on 2017/9/28.
 */

public class ClassUser extends BmobUser {

    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    private String password;
    private String cellphone;
}
