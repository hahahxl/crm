package com.bytedance.crm.setting.servive.impl;

import com.bytedance.crm.setting.dao.DicTypeDao;
import com.bytedance.crm.setting.dao.DicValueDao;
import com.bytedance.crm.setting.domain.DicType;
import com.bytedance.crm.setting.domain.DicValue;
import com.bytedance.crm.setting.servive.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/16.
 */
//@Service
public class DicServiceImpl implements DicService {
    //    @Autowired
    private DicTypeDao dicTypeDao = null;
    //  @Autowired
    private DicValueDao dicValueDao = null;

    public void setDicTypeDao(DicTypeDao dicTypeDao) {
        this.dicTypeDao = dicTypeDao;
    }

    public void setDicValueDao(DicValueDao dicValueDao) {
        this.dicValueDao = dicValueDao;
    }


    @Override
    public Map<String, List<DicValue>> getAllDic() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dicTypes = dicTypeDao.getDicType();
        for (DicType dicType : dicTypes) {
            String dicTypeCode = dicType.getCode();
            List<DicValue> dicValues = dicValueDao.getDicValue(dicTypeCode);
            map.put(dicTypeCode, dicValues);
        }
        return map;
    }
}
