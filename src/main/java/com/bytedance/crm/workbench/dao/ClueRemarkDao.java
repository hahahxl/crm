package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getClueRemarkListByClueId(String clueId);

    int delete(String id);
}
