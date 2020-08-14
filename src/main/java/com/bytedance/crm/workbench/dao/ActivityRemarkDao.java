package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * Created by hxl on 2020/8/13.
 */
public interface ActivityRemarkDao {
    List<ActivityRemark> getRemarkListByAid(String id);

    int deleteRemark(String id);

    int updateRemark(ActivityRemark activityRemark);

    ActivityRemark getRemarkById(String id);
}
