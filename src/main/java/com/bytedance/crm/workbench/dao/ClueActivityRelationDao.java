package com.bytedance.crm.workbench.dao;


public interface ClueActivityRelationDao {


    int unbund(String id);

    int bund(String uuid, String clueId, String aid);

}
