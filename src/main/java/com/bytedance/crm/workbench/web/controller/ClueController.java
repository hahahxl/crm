package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.setting.servive.DicService;
import com.bytedance.crm.setting.servive.impl.UserService;
import com.bytedance.crm.utils.DateTimeUtil;
import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.activityVo.ActivityVo;
import com.bytedance.crm.workbench.activityVo.PageListVo;
import com.bytedance.crm.workbench.domain.Activity;
import com.bytedance.crm.workbench.domain.ActivityRemark;
import com.bytedance.crm.workbench.domain.Clue;
import com.bytedance.crm.workbench.service.ActivityService;
import com.bytedance.crm.workbench.service.ClueService;
import com.github.pagehelper.PageHelper;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/7.
 */
@Controller
public class ClueController {
    @Autowired
    private DicService dicService = null;
    @Autowired
    private UserService userService = null;
    @Autowired
    private ClueService clueService = null;
    @Autowired
    private ActivityService activityService = null;

    @RequestMapping("/workbench/clue/getUserList.do")
    @ResponseBody
    /**
     * clue表单用户下拉框的用户信息提取
     */
    public List<User> getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("----------执行clue功能的getUserList方法--------++");
        System.out.println("userService::" + userService);
        List<User> users = userService.getUserList();
        return users;
    }

    @RequestMapping("/workbench/clue/save.do")
    @ResponseBody
    /**
     * clue表单用户下拉框的用户信息提取
     */
    public Map<String, Object> save(HttpServletRequest request, Clue clue) {
        System.out.println("----------执行clue功能的save方法--------++");
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        Map<String, Object> map = clueService.save(clue);
        return map;
    }


    @RequestMapping("/workbench/clue/pageList.do")
    @ResponseBody
    /**
     * clue列表展示
     */
    public Map<String, Object> pageList(HttpServletRequest request, Clue clue) {
        System.out.println("----------执行clue功能的pageList方法--------++");
        System.out.println("clue:::::::" + clue);
        String fullname = request.getParameter("fullname");
        System.out.println("fullname::::" + fullname);
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        Map<String, Object> map = clueService.pageListByCondition(clue);
        return map;
    }


    @RequestMapping("/workbench/clue/detail.do")
    @ResponseBody
    /**
     * clue详细列表展示
     */
    public ModelAndView detail(String id) {
        System.out.println("----------执行clue功能的detail方法--------++");
        Clue clue = clueService.detailById(id);
        ModelAndView mav = new ModelAndView();
        System.out.println("clue gg :::" + clue);

        mav.addObject("clue", clue);
        mav.setViewName("forward:/workbench/clue/detail.jsp");
        return mav;
    }

    @RequestMapping("/workbench/clue/getActivityListByClueId.do")
    @ResponseBody
    /**
     * clue活动关联列表展示
     */
    public List<Activity> getActivityListByClueId(String clueId) {
        System.out.println("----------执行clue功能的getActivityListByClueId方法--------++");
        List<Activity> activitys = activityService.getActivityListByClueId(clueId);
        return activitys;
    }


    @RequestMapping("/workbench/clue/unbund.do")
    @ResponseBody
    /**
     * clue接触关联活动列表
     */
    public boolean unbund(String id) {
        System.out.println("----------执行clue功能的unbund方法--------++");
        boolean flag = clueService.unbund(id);
        return flag;
    }

    @RequestMapping("/workbench/clue/getActivityListByBund.do")
    @ResponseBody
    /**
     * clue添加关联活动列表的展示
     */
    public List<Activity> getActivityListByBund(String clueId, String name) {
        System.out.println("----------执行clue功能的getActivityListByBund方法--------++");
        System.out.println("clueId ::: " + clueId + "   name:::" + name);

        List<Activity> activities = activityService.getActivityListByBund(clueId, name);
        for (Activity activity : activities) {
            System.out.println("bund::" + activity);
        }
        return activities;
    }

    @RequestMapping("/workbench/clue/bund.do")
    @ResponseBody
    /**
     * clue添加关联活动列表
     */
    public boolean bund(HttpServletRequest request) {
        System.out.println("----------执行clue功能的bund方法--------++");
        String clueId = request.getParameter("clueId");
        String[] aids = request.getParameterValues("aid");
        boolean flag = clueService.bund(clueId, aids);
        System.out.println("flag:::" + flag);
        return flag;
    }

    @RequestMapping("/workbench/clue/getActivityListByConvent.do")
    @ResponseBody
    /**
     * convent查询活动列表
     */
    public List<Activity> getActivityListByConvent(String name) {
        System.out.println("----------执行convent功能的getActivityListByConvent方法--------++");
        System.out.println("nameww:::" + name);
        List<Activity> activities = activityService.getActivityListByConvent(name);

        return activities;
    }
}
