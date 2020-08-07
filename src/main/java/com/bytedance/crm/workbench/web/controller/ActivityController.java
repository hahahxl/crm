package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by hxl on 2020/8/7.
 */
@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService = null;
    @RequestMapping("/workbench/activity/getUserList.do")
    @ResponseBody
    public List<User> getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("------------------------------------------++");
       List<User> users= activityService.getUserList();
        for (User user : users) {
            System.out.println(user+"-=============");
        }
        return users;

    }
}
