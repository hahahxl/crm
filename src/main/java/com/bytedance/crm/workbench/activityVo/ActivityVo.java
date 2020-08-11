package com.bytedance.crm.workbench.activityVo;

/**
 * Created by hxl on 2020/8/10.
 */
public class ActivityVo {
    private String activityMsg;
    private boolean success;

    public String getActivityMsg() {
        return activityMsg;
    }

    public void setActivityMsg(String activityMsg) {
        this.activityMsg = activityMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActivityVo{" +
                "activityMsg='" + activityMsg + '\'' +
                ", success=" + success +
                '}';
    }
}
