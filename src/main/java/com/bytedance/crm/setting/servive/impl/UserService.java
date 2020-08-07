package com.bytedance.crm.setting.servive.impl;

import com.bytedance.crm.setting.domain.User;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by hxl on 2020/8/5.
 */
public interface UserService {
    User login(String loginAct, String loginPwd) throws LoginException;

}
