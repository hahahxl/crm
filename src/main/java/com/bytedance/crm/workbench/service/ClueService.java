package com.bytedance.crm.workbench.service;

import com.bytedance.crm.workbench.domain.Clue;
import com.bytedance.crm.workbench.domain.Tran;

import java.util.Map;

/**
 * Created by hxl on 2020/8/18.
 */
public interface ClueService {
    Map<String, Object> save(Clue clue);

    Map<String, Object> pageListByCondition(Clue clue);

    Clue detailById(String id);

    boolean unbund(String id);

    boolean bund(String clueId, String[] aids);

    boolean convert(String clueId, Tran tran, String createBy);
}
