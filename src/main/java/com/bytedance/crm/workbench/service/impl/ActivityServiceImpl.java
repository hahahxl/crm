package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
   //     PageHelper.startPage(2, 3);
        List<User> users = activityDao.getUserList();
      /*  System.out.println("---------------------");

        for (User user : users) {
            System.out.println("user::::"+user);
        }
        System.out.println("----------------------------");
       */ return users;
    }

    @Override
    public ActivityVo save(Activity activity) {
        ActivityVo activityVo = new ActivityVo();
        int num = activityDao.save(activity);
        if (num > 0) {
            activityVo.setSuccess(true);
            activityVo.setActivityMsg("创建成功");
        } else {
            activityVo.setActivityMsg("创建失败");
            activityVo.setSuccess(false);
        }
        return activityVo;

    }

    @Override
    public List<Activity> getPageList(Map<String,String[]> parameterMap) {
        List<Activity> pageList =activityDao.getPageList(parameterMap);
        return pageList;
    }

    @Override
    public int getTotal(Map<String,String[]> parameterMap) {
      int total=  activityDao.getToal(parameterMap);
        return total;
    }
}
