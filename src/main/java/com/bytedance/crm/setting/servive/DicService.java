package com.bytedance.crm.setting.servive;

import com.bytedance.crm.setting.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/16.
 */
public interface DicService {
    Map<String, List<DicValue>> getAllDic();
}
