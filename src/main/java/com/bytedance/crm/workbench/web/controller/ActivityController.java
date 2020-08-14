package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.utils.DateTimeUtil;
import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.activityVo.PageListVo;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.domain.ActivityRemark;
import com.bytedance.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    /**
     * 表单用户下拉框的用户信息提取
     */
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
    /*
    * 活动的创建
    * */
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
    /**
     * 活动列表的展示以及分页
     */
    public PageListVo<Activity> pageList(HttpServletRequest request) {
        System.out.println("执行pageList方法-----------------------++");
        Map<String, String[]> parameterMap = request.getParameterMap();
        PageListVo<Activity> pageList = new PageListVo<>();
        PageHelper.startPage(Integer.parseInt(parameterMap.get("pageNo")[0]), Integer.parseInt(parameterMap.get("pageSize")[0]));
        List<Activity> userList = activityService.getPageList(parameterMap);
        pageList.setDataList(userList);
        int total = activityService.getTotal(parameterMap);
        pageList.setTotal(total);
        return pageList;
    }


    @RequestMapping("/workbench/activity/getUserListAndActivity.do")
    @ResponseBody
    /*
    * 市场活动列表的删除
    * */
    public Map<String, Object> getUserListAndActivity(String id) {
        System.out.println("执行getUserListAndActivity方法-----------------------++");
        Map<String, Object> userListAndActivity = activityService.getUserListAndActivity(id);
        return userListAndActivity;
    }


    @RequestMapping("/workbench/activity/delete.do")
    @ResponseBody
    /*
    * 市场活动列表的删除
    * */
    public ActivityVo delete(HttpServletRequest request) {
        System.out.println("执行delete方法-----------------------++");
        String[] ids = request.getParameterValues("id");
        ActivityVo activityVo = activityService.delete(ids);
        return activityVo;
    }


    @RequestMapping("/workbench/activity/update.do")
    @ResponseBody
    /*
    * 市场活动列表的修改
    * */
    public ActivityVo update(Activity activity, HttpServletRequest request) {
        System.out.println("执行getUserListAndActivity方法-----------------------++");
        User user = (User) request.getSession().getAttribute("user");
        String editName = user.getName();
        String editTime = DateTimeUtil.getSysTime();
        activity.setEditBy(editName);
        activity.setEditTime(editTime);
        System.out.println("activity:::" + activity);
        ActivityVo activityVo = activityService.update(activity);
        return activityVo;
    }

    @RequestMapping("/workbench/activity/detail.do")
    // @ResponseBody
    /*
    * 市场活动详细信息展示
    * */
    public ModelAndView detail(String id) {
        System.out.println("执行detail.do方法-----------------------++");
        Activity activity = activityService.detail(id);
        System.out.println("activity::" + activity);
        ModelAndView mav = new ModelAndView();
        mav.addObject("activity", activity);
        mav.setViewName("forward:/workbench/activity/detail.jsp");
        return mav;
    }


    @RequestMapping("/workbench/activity/getRemarkListByAid.do")
    @ResponseBody
    /*
    * 活动备注列表的展示
    * */
    public List<ActivityRemark> getRemarkListByAid(String id) {
        System.out.println("执行getRemarkListByAid方法-----------------------++");
        List<ActivityRemark> activityRemark = activityService.getRemarkListByAid(id);
        for (ActivityRemark remark : activityRemark) {
            System.out.println("remark::" + remark);
        }
        return activityRemark;
    }


    @RequestMapping("/workbench/activity/deleteRemark.do")
    @ResponseBody
    /*
    * 活动备注的删除
    * */
    public boolean deleteRemark(String id) {
        System.out.println("==========执行deleteRemark方法-----------------------++");
        boolean flag = activityService.deleteRemark(id);
        System.out.println("flag::" + flag);
        return flag;
    }


    @RequestMapping("/workbench/activity/updateRemark.do")
    @ResponseBody
    /*
    * 活动备注的修改
    * */
    public Map<String, Object> updateRemark(HttpServletRequest request, ActivityRemark activityRemark) {
        System.out.println("==========执行updateRemark方法-----------------------++");
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        User user = (User) request.getSession().getAttribute("user");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditFlag("1");
        System.out.println("activityRemark:::" + activityRemark);
        Map<String, Object> map = activityService.updateRemark(activityRemark);
        System.out.println("success::" + map.get("success"));
        System.out.println("activityRemark::::" + map.get("activityRemark"));
        return map;
    }

    @RequestMapping("/workbench/activity/addRemark.do")
    @ResponseBody
    /*
    * 活动备注的增加
    * */
    public Map<String, Object> addRemark(HttpServletRequest request, ActivityRemark activityRemark) {
        System.out.println("==========执行addRemark方法-----------------------++");
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        activityRemark.setEditFlag("0");
        Map<String,Object> map = activityService.addRemark(activityRemark);
        System.out.println("activityRemark add remark:::"+map.get("activityRemark"));
        return map;
    }

}
