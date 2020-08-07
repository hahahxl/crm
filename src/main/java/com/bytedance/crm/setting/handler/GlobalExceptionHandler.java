package com.bytedance.crm.setting.handler;

import com.bytedance.crm.setting.exception.UserLoginException;
import com.bytedance.crm.setting.loginMsgVo.LoginInfo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ControllerAdvice : 控制器增强（也就是说给控制器类增加功能--异常处理功能）
 * 位置：在类的上面。
 * 特点：必须让框架知道这个注解所在的包名，需要在springmvc配置文件声明组件扫描器。
 * 指定@ControllerAdvice所在的包名
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //处理其它异常， NameException, AgeException以外，不知类型的异常

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public LoginInfo doOtherException(HttpServletRequest request, Exception exception) {

        System.out.println("++++++" + exception.getMessage());
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setMsg(exception.getMessage());
        loginInfo.setSuccess(false);
        return loginInfo;
    }

}
