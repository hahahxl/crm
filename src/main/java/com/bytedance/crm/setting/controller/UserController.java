package com.bytedance.crm.setting.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.setting.loginMsgVo.LoginInfo;
import com.bytedance.crm.setting.servive.impl.UserService;
import com.bytedance.crm.setting.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by hxl on 2020/8/5.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService = null;

    @RequestMapping(value = "/user/setting/login.do")
     @ResponseBody
    public LoginInfo login(HttpServletRequest request, User user) throws LoginException {
        String loginPwd = MD5Util.getMD5(user.getLoginPwd());
        User login = userService.login(user.getLoginAct(), loginPwd);
        LoginInfo loginInfo = new LoginInfo();//执行到这，说明没有抛出任何异常，
        loginInfo.setSuccess(true);
        request.getSession().setAttribute("user", login);//
        return loginInfo;
    }
}
