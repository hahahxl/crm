package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.Tran;

public interface TranDao {

    int save(Tran tran);

    Tran detail(String tranId);

    int changeStage(Tran tran);
}
