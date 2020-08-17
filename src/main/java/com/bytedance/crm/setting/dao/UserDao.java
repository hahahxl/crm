package com.bytedance.crm.setting.dao;

import com.bytedance.crm.setting.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/5.
 */
public interface UserDao {
    User login(Map<String, String> map);

    List<User> getUserList();
}
