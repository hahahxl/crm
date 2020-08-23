package com.bytedance.crm.setting.servive.impl;

import com.bytedance.crm.setting.dao.UserDao;
import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.setting.exception.UserLoginException;
import com.bytedance.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/5.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao = null;

    @Override
    public User login(String loginAct, String loginPwd) throws LoginException {
        Map<String, String> map = new HashMap();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (user == null) {
            throw new UserLoginException("用户名或密码错误");
        }
        if (DateTimeUtil.getSysTime().compareTo(user.getExpireTime()) > 0) {
            throw new UserLoginException("该用户已失效");
        }

        if ("0".equals(user.getLockState())) {
            throw new UserLoginException("该用户已被锁定，请尝试联系管理员");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }

}
