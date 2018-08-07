package org.cloudpan.web.Interceptor;

import org.cloudpan.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TracyM on 2017/3/23.
 */

public class UserLoginInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        /*
         * 从session中获取用户信息
         */
        User user = (User) request.getSession().getAttribute("User");

        if(user == null){//如果session中没有用户的信息，跳转到登录页面，内部网页不能访问
            logger.info("UserLoginInterceptor---->>>>>>preHandle");
            request.getRequestDispatcher("index.jsp").forward(request, response);//TODO
            return false;
        }else
            return true;
    }

}
