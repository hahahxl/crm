package com.bytedance.crm.setting.dao;

import com.bytedance.crm.setting.domain.DicValue;

import java.util.List;

/**
 * Created by hxl on 2020/8/16.
 */
public interface DicValueDao {
    List<DicValue> getDicValue(String dicTypeCode);
}
