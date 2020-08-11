package com.bytedance.crm.workbench.activityVo;

import java.util.List;

/**
 * Created by hxl on 2020/8/11.
 */
public class PageListVo<T> {
    private List<T> dataList;
    private Integer total;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageListVo{" +
                "dataList=" + dataList +
                ", total=" + total +
                '}';
    }
}
