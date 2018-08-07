package org.cloudpan.web;

import org.cloudpan.dto.UserInfo;
import org.cloudpan.entity.User;
import org.cloudpan.service.UserService;
import org.cloudpan.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by TracyM on 2017/3/23.
 */
@RequestMapping("/user")
@Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //盐值用于混肴md5字符串
    private String salt = "sYu&dbhsUI0x5*#skuS!(ss)";

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param request
     * @param response
     * @param userPhone
     * @param userPwd
     * @return
     */
    @RequestMapping(value="/login",method=RequestMethod.POST,
                    produces={"application/json;charset=UTF-8"})
    @ResponseBody
    public UserInfo login(HttpServletRequest request, HttpServletResponse response ,long userPhone, String userPwd) throws Exception{

        int isUserExist = userService.isExistByPhone(userPhone);
        User user=userService.isExist(userPhone, userPwd);


        if (isUserExist==0){
            userService.addUser(userPhone,userPwd);

            UserInfo userInfo = new UserInfo();

            userInfo.setUserPhone(userPhone);
            userInfo.setUserName("newUser");
            userInfo.setIsvip((short) 0);
            userInfo.setSuccess((short) 1);

            //在Session里保存信息
            HttpSession session = request.getSession();
            session.setAttribute("UserInfo", userInfo);
            session.setMaxInactiveInterval(3600);

            return userInfo;
        }

        else if (user==null){
            UserInfo userInfo = new UserInfo();

            userInfo.setSuccess((short) 0);
            logger.info("Login failed!");

//            //在Session里保存信息
//            HttpSession session = request.getSession();
//            session.setAttribute("UserInfo", userInfo);
//            session.setMaxInactiveInterval(3600);

            return userInfo;
        }else{
            UserInfo userInfo = new UserInfo();

            userInfo.setUserPhone(user.getUserPhone());
            userInfo.setUserName(user.getUserName());
            userInfo.setIsvip(user.getIsvip());
            userInfo.setSuccess((short) 1);

            logger.info("UserController.Login()"+"ID&Phone&Pwd:"+user.getUserId()+"&"+user.getUserPhone()+"&"+user.getUserPwd());
            //在Session里保存信息
            HttpSession session = request.getSession();
            session.setAttribute("UserInfo", userInfo);
            session.setMaxInactiveInterval(3600);

            return userInfo;
        }
    }

    private String getUserIdMd5(String userId){
        String base = userId+"/"+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}


