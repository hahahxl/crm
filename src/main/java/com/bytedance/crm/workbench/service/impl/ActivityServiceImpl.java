package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hxl on 2020/8/7.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
   @Autowired
    private ActivityDao activityDao = null;
   /* @Override
    public Activity selectAll() {
        Activity activity = activityDao.selectAll();
        System.out.println("selallservice====================");
        return activity;
    }*/

    @Override
    public List<User> getUserList() {
       List<User> users = activityDao.getUserList();
        return users;
    }
}
