package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.setting.dao.UserDao;
import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.dao.ActivityDao;
import com.bytedance.crm.workbench.dao.ActivityRemarkDao;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.domain.ActivityRemark;
import com.bytedance.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/7.
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityDao activityDao = null;
    @Autowired
    private UserDao userDao = null;
    @Autowired
    private ActivityRemarkDao activityRemarkDao = null;
   /* @Override
    public Activity selectAll() {
        Activity activity = activityDao.selectAll();
        System.out.println("selallservice====================");
        return activity;
    }*/

    @Override
    public List<User> getUserList() {
        //     PageHelper.startPage(2, 3);
        List<User> users = userDao.getUserList();
      /*  System.out.println("---------------------");

        for (User user : users) {
            System.out.println("user::::"+user);
        }
        System.out.println("----------------------------");
       */
        return users;
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
    public List<Activity> getPageList(Map<String, String[]> parameterMap) {
        List<Activity> pageList = activityDao.getPageList(parameterMap);
        return pageList;
    }

    @Override
    public int getTotal(Map<String, String[]> parameterMap) {
        int total = activityDao.getToal(parameterMap);
        return total;
    }

    @Override
    public ActivityVo delete(String[] ids) {
        boolean flag = true;
        int num = activityDao.delete(ids);
        if (num < 0) {
            flag = false;
        }
        if (ids.length != num) {
            flag = false;
        }
        ActivityVo activityVo = new ActivityVo();
        activityVo.setSuccess(flag);
        return activityVo;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> userList = userDao.getUserList();
        Activity activity = activityDao.getActivityById(id);
        Map<String, Object> userListAndActivity = new HashMap<>();
        userListAndActivity.put("userList", userList);
        userListAndActivity.put("activity", activity);
        return userListAndActivity;
    }

    @Override
    public ActivityVo update(Activity activity) {
        int num = activityDao.update(activity);
        boolean flag = true;
        if (num <= 0) {
            flag = false;
        }
        ActivityVo activityVo = new ActivityVo();
        activityVo.setSuccess(flag);
        return activityVo;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        List<ActivityRemark> activityRemark = activityRemarkDao.getRemarkListByAid(id);
        return activityRemark;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;
        int num = activityRemarkDao.deleteRemark(id);
        if (num <= 0) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> updateRemark(ActivityRemark activityRemark) {
        Map<String, Object> map = new HashMap<>();
        ActivityRemark activityRemark2 = null;
        boolean flag = true;
        int num = activityRemarkDao.updateRemark(activityRemark);
        if (num <= 0) {
            flag = false;
        }
        if (flag) {
            activityRemark2 = activityRemarkDao.getRemarkById(activityRemark.getId());
        }
        map.put("success", flag);
        map.put("activityRemark", activityRemark2);
        return map;
    }

    @Override
    public Map<String, Object> addRemark(ActivityRemark activityRemark) {
        boolean flag = true;
        ActivityRemark activityRemark1 = null;
        int num = activityRemarkDao.addRemark(activityRemark);
        if (num <= 0) {
            flag = false;
        }
        if (flag) {
            activityRemark1 = activityRemarkDao.getRemarkById(activityRemark.getId());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("activityRemark", activityRemark1);
        return map;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> activities = activityDao.getActivityListByClueId(clueId);
        return activities;
    }

    @Override
    public List<Activity> getActivityListByBund(String clueId, String name) {
        List<Activity> activities = activityDao.getActivityListByBund(clueId, name);
        return activities;
    }

    @Override
    public List<Activity> getActivityListByConvent(String name) {
        List<Activity> activities = activityDao.getActivityListByConvent(name);
        return activities;
    }


}
