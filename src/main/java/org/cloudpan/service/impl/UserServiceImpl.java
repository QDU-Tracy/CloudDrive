package org.cloudpan.service.impl;

import org.cloudpan.dao.FileDao;
import org.cloudpan.dao.RecordDao;
import org.cloudpan.dao.UserDao;
import org.cloudpan.entity.User;
import org.cloudpan.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by TracyM on 2017/3/22.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private FileDao fileDao;

    //盐值用于混肴md5字符串
    private String salt = "sYu&dbhsUI0x5*#skuS!(ss)";

    @Override
    public List<User> getUserList() {
        return userDao.queryAll(0, 10);
    }

    @Override
    public void addUser(long userPhone,String userPwd) {
        userDao.addUser(userPhone,  getPwdMd5(userPwd+salt));
    }

    @Override
    public User isExist(long userPhone,String userPwd) {
        User user=userDao.queryOne(userPhone, getPwdMd5(userPwd+salt));
        if (user==null){
            return null;
        } else
            return user;
    }

    @Override
    public long queryUserId(long userPhone) {
        long userId=userDao.queryUserId(userPhone);
        return userId;
    }

    @Override
    public int isExistByPhone(long userPhone) {
        return userDao.queryIfUserExist(userPhone);
    }

    @Override
    public String getUserNameById(long userId) {
        String userName = userDao.queryNameById(userId);
        return userName;
    }


    private String getPwdMd5(String userPwd){
        String base = userPwd+"/"+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
