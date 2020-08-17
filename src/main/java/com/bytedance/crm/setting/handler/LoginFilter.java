package com.bytedance.crm.setting.handler;

import com.bytedance.crm.setting.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by hxl on 2020/8/6.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("进入到验证有没有登录过的过滤器");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();
        System.out.println("path::" + path);
        //不应该被拦截的资源，自动放行请求
        System.out.println("\"/login.jsp\".equals(path)" + ("/login.jsp".equals(path)));
        System.out.println("\"/setting/user/login.do\".equals(path)" + ("/user/setting/login.do".equals(path)));
        if ("/login.jsp".equals(path) || "/user/setting/login.do".equals(path)) {
            chain.doFilter(req, resp);
            //其他资源必须验证有没有登录过
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            //如果user不为null，说明登录过
            if (user != null) {
                chain.doFilter(req, resp);
                //没有登录过
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");

            }


        }


    }

    @Override
    public void destroy() {

    }
}
