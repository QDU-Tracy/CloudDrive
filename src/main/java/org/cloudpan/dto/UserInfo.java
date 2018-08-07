package org.cloudpan.dto;

/**
 * Created by Tracy on 2017/4/24.
 */
public class UserInfo {

    private long userPhone;
    private String userName;
    private short isvip;
    private short success;

    public UserInfo() {
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public short getIsvip() {
        return isvip;
    }

    public void setIsvip(short isvip) {
        this.isvip = isvip;
    }

    public short getSuccess() {
        return success;
    }

    public void setSuccess(short success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userPhone=" + userPhone +
                ", userName='" + userName + '\'' +
                ", isvip=" + isvip +
                ", success=" + success +
                '}';
    }
}
