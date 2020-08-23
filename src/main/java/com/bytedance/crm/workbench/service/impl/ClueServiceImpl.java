package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.dao.ClueActivityRelationDao;
import com.bytedance.crm.workbench.dao.ClueDao;
import com.bytedance.crm.workbench.domain.Clue;
import com.bytedance.crm.workbench.service.ClueService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/18.
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao = null;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao = null;

    @Override
    public Map<String, Object> save(Clue clue) {
        boolean flag = true;
        Map<String, Object> map = new HashMap<>();
        int num = clueDao.save(clue);
        if (num <= 0) {
            flag = false;
        }

        map.put("success", flag);
        return map;
    }

    @Override
    public Map<String, Object> pageListByCondition(Clue clue) {
        Map<String, Object> map = new HashMap<>();
        List<Clue> clues = clueDao.clueListByCondition(clue);
        int total = clueDao.total(clue);
        map.put("dataList", clues);
        map.put("total", total);
        return map;
    }

    @Override
    public Clue detailById(String id) {
        Clue clue = clueDao.getClueById(id);
        return clue;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = true;
        int num = clueActivityRelationDao.unbund(id);
        if (num <= 0) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bund(String clueId, String[] aids) {
        boolean flag = true;
        for (String aid : aids) {
            String uuid = UUIDUtil.getUUID();
            int num = clueActivityRelationDao.bund(uuid, clueId, aid);
            if (num <= 0) {
                flag = false;
            }
        }

        return flag;
    }
}
