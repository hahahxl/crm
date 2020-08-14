package com.bytedance.crm.workbench.service;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/7.
 */
public interface ActivityService {
    List<User> getUserList();

    ActivityVo save(Activity activity);

    List<Activity> getPageList(Map<String, String[]> parameterMap);

    int getTotal(Map<String, String[]> parameterMap);

    ActivityVo delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    ActivityVo update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String id);

    boolean deleteRemark(String id);

    Map<String,Object> updateRemark(ActivityRemark activityRemark);

    Map<String,Object> addRemark(ActivityRemark activityRemark);
}
