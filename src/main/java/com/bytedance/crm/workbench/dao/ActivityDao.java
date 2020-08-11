package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/7.
 */
public interface ActivityDao {

    List<User> getUserList();

    int save(Activity activity);

    List<Activity> getPageList(Map<String, String[]> parameterMap);

    int getToal(Map<String, String[]> parameterMap);
}
