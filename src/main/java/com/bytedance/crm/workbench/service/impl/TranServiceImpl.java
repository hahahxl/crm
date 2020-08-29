package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.dao.CustomerDao;
import com.bytedance.crm.workbench.dao.TranDao;
import com.bytedance.crm.workbench.dao.TranHistoryDao;
import com.bytedance.crm.workbench.domain.Tran;
import com.bytedance.crm.workbench.domain.TranHistory;
import com.bytedance.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/28.
 */
@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao = null;
    @Autowired
    private CustomerDao customerDao = null;
    @Autowired
    private TranHistoryDao tranHistoryDao = null;

    @Override
    public boolean save(Tran tran, String customerName) {
        boolean flag = true;
        String customerId = customerDao.getIdByName(customerName);
        tran.setCustomerId(customerId);
        int count = tranDao.save(tran);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Tran detail(String tranId) {
        Tran tran = tranDao.detail(tranId);
        return tran;
    }

    @Override
    public List<TranHistory> getTranHistoryList(String tranId) {
        List<TranHistory> tranHistories = tranHistoryDao.getTranHistoryList(tranId);
        return tranHistories;
    }

    @Override
    public boolean changeStage(Tran tran, String createBy, String createTime) {
        boolean flag = true;
        int count = tranDao.changeStage(tran);
        if (count != 1) {
            flag = false;
        }
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        Map<String, Object> map = new HashMap<>();
        map.put("tran", tran);
        map.put("tranHistoryId", UUIDUtil.getUUID());
        int count2 = tranHistoryDao.insert(map);
        if (count2 != 1) {
            flag = false;
        }
        return flag;
    }
}
