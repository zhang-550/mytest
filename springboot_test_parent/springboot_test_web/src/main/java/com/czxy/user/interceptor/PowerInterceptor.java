package com.czxy.user.interceptor;

import com.czxy.user.pojo.User;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PowerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            response.sendRedirect("/login/error.html?msg=a1");
            return false;
        }
        if (request.getRequestURI().contains("updateUser.html")){
            if (user.getRid()!=1){
                response.sendRedirect("/login/error.html?msg=a2");
                return false;
            }
        }
        return true;
    }
}
