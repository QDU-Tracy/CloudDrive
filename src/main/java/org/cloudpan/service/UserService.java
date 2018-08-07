package org.cloudpan.service;

import org.cloudpan.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by TracyM on 2017/3/22.
 */
public interface UserService {

    List<User> getUserList();

    void addUser(long userPhone,String userPwd);

    User isExist(long userPhone,String userPwd);

    long queryUserId(long userPhone);

    int isExistByPhone(long userPhone);

    String getUserNameById(long userId);
}
