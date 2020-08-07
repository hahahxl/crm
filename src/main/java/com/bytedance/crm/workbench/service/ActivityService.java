package com.bytedance.crm.workbench.service;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.domain.Activity;

import java.util.List;

/**
 * Created by hxl on 2020/8/7.
 */
public interface ActivityService {
    List<User> getUserList();
}
