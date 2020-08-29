package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.Tran;
import com.bytedance.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranHistoryDao {

    int save(TranHistory tranHistory);

    List<TranHistory> getTranHistoryList(String tranId);

    int insert(Map<String, Object> map);
}
