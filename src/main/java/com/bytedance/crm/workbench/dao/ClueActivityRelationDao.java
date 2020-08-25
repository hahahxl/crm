package com.bytedance.crm.workbench.dao;


import com.bytedance.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(String uuid, String clueId, String aid);

    List<ClueActivityRelation> getListByClueId(String clueId);

    int delete(String id);
}
