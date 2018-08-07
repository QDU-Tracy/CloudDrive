package org.cloudpan.service;

import org.cloudpan.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by TracyM on 2017/3/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class UserServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Test
    public void TestgetUserList(){
        List<User> list = userService.getUserList();
        logger.info("list={}", list);
    }

    @Test
    public void TestaddUser(){
        userService.addUser(111, "111");
    }

    @Test
    public void TestaddRecord(){

    }

    @Test
    public void TestIsExist(){
        userService.isExist(111L, "111");
    }

    @Test
    public void TestIsExistByPhone(){
        int count = userService.isExistByPhone(111);
        System.out.println(count);
    }
}
