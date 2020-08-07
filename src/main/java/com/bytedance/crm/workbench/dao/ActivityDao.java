package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.domain.Activity;

import java.util.List;

/**
 * Created by hxl on 2020/8/7.
 */
public interface ActivityDao {

    List<User> getUserList();
}
