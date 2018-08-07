package org.cloudpan.dao;

import org.apache.ibatis.annotations.Param;
import org.cloudpan.entity.User;

import java.util.List;

/**
 * Created by TracyM on 2017/3/22.
 */
public interface UserDao {
    /**
     *
     * 添加用户
     * @param userPhone
     * @param userPwd
     * @return
     */
    int addUser(@Param("userPhone") long userPhone,@Param("userPwd") String userPwd);


    /**
     * @param offset
     * @param limit
     * @return
     */
    List<User> queryAll(@Param("offset")int offset, @Param("limit")int limit);

    User queryOne(@Param("userPhone") long userPhone, @Param("userPwd") String userPwd);

    long queryUserId(@Param("userPhone") long userPhone);

    int queryIfUserExist(@Param("userPhone") long userPhone);

    String queryNameById(@Param("userId") long userId);


}
