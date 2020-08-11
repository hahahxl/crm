package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.utils.DateTimeUtil;
import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.activityVo.PageListVo;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        List<User> users = activityService.getUserList();
        for (User user : users) {
            System.out.println(user + "-=============");
        }
        return users;

    }


    @RequestMapping("/workbench/activity/save.do")
    @ResponseBody
    public ActivityVo save(HttpServletRequest request, Activity activity) {
        System.out.println("执行save方法-----------------------++");
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        System.out.println(activity + ":::::::");
        ActivityVo activityVo = activityService.save(activity);

        return activityVo;

    }

    @RequestMapping("/workbench/activity/pageList.do")
    @ResponseBody
    public PageListVo<Activity> pageList(HttpServletRequest request) {
        System.out.println("执行pageList方法-----------------------++");
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("--aaaaaaaaaaaaaaaaaaaaaparameter"+parameterMap);
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            System.out.println(stringEntry.getKey()+"::::"+stringEntry.getValue()[0]);
        }
        PageListVo<Activity> pageList = new PageListVo<>();
       PageHelper.startPage(Integer.parseInt(parameterMap.get("pageNo")[0]),Integer.parseInt(parameterMap.get("pageSize")[0]));
        List<Activity> userList = activityService.getPageList(parameterMap);
        pageList.setDataList(userList);
        int total = activityService.getTotal(parameterMap);
        pageList.setTotal(total);
        return pageList;
    }
}
