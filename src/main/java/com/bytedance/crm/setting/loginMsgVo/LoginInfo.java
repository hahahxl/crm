package com.bytedance.crm.setting.loginMsgVo;

/**
 * Created by hxl on 2020/8/6.
 */
public class LoginInfo {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }
}
