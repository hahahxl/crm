package com.bytedance.crm.workbench.dao;


import com.bytedance.crm.workbench.domain.Clue;

import java.util.List;

public interface ClueDao {


    int save(Clue clue);

    List<Clue> clueListByCondition(Clue clue);

    int total(Clue clue);

    Clue getClueById(String id);
}
