package org.cloudpan.entity;

/**
 * Created by TracyM on 2017/3/22.
 */
public class User {
    private long userId;
    private long userPhone;
    private String userName;
    private String userPwd;
    private short isvip;

//    一开始没有写默认的构造方法，导致报错No constructor found in[....]......
    public User() {
    }

    public User(long userId, long userPhone, String userName, String userPwd, short isvip) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.userName = userName;
        this.userPwd = userPwd;
        this.isvip = isvip;
    }

    public User(long userPhone, String userName, String userPwd) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.userPwd = userPwd;
    }



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public short getIsvip() {
        return isvip;
    }

    public void setIsvip(short isvip) {
        this.isvip = isvip;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPhone=" + userPhone +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", isvip=" + isvip +
                '}';
    }
}
