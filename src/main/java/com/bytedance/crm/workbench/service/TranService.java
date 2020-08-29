package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.domain.Tran;
import com.bytedance.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * Created by hxl on 2020/8/28.
 */
public interface TranService {
    boolean save(Tran tran, String customerName);

    Tran detail(String tranId);

    List<TranHistory> getTranHistoryList(String tranId);

    boolean changeStage(Tran tran, String createBy, String createTime);
}
