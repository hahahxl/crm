package com.bytedance.crm.setting.handler;

import com.bytedance.crm.setting.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hxl on 2020/8/6.
 */
public class LoginHandeler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行过滤器");

        String uri = request.getRequestURI();
        System.out.println("uri::" + uri);
        System.out.println("uri.indexOf(\"/login.do\")::::" + uri.indexOf("/login.do"));
        System.out.println(" uri.indexOf(\"/login.jsp\")::::" + uri.indexOf("/login.jsp"));
        if (uri.indexOf("/login.do") >= 0 || uri.indexOf("/login.jsp") >= 0) {
            return true;
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            /*用户未登录，请先登录*/
            return true;
        }
        return false;
    }
}
