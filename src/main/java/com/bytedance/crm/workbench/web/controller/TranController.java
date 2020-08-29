package com.bytedance.crm.workbench.web.controller;

import com.bytedance.crm.setting.domain.User;
import com.bytedance.crm.setting.servive.impl.UserService;
import com.bytedance.crm.utils.DateTimeUtil;
import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.domain.Tran;
import com.bytedance.crm.workbench.domain.TranHistory;
import com.bytedance.crm.workbench.service.CustomerService;
import com.bytedance.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/27.
 */
@Controller
public class TranController {
    @Autowired
    private UserService userService = null;
    @Autowired
    private CustomerService customerService = null;
    @Autowired
    private TranService tranService = null;


    /*所有者下拉框的处理*/
    @RequestMapping("/workbench/transaction/getUserList.do")
    public ModelAndView getUserList() {
        List<User> userList = userService.getUserList();
        ModelAndView mav = new ModelAndView();
        mav.addObject("userList", userList);
        mav.setViewName("forward:/workbench/transaction/save.jsp");
        return mav;
    }


    /*客户名字的模糊查询，完成自动补全*/
    @RequestMapping("/workbench/transaction/getCustomerName.do")
    @ResponseBody
    public List<String> getCustomerName(String name) {
        System.out.println("name:::" + name);
        List<String> list = customerService.getCustomerName(name);
        return list;
    }

    /*新建交易*/
    @RequestMapping("/workbench/transaction/saveTransaction.do")
    @ResponseBody
    public ModelAndView saveTransaction(HttpServletRequest request, Tran tran) {
        System.out.println("-----------执行saveTransaction方法");
        ModelAndView mav = new ModelAndView();
        System.out.println("tran:::" + tran);
        String customerName = request.getParameter("customerName");
        System.out.println("customerName:::" + customerName);
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = tranService.save(tran, customerName);
        if (flag) {
            mav.setViewName("redirect:/workbench/transaction/index.jsp");
        }
        return mav;
    }


    /*交易detail的处理*/
    @RequestMapping("/workbench/transaction/detail.do")
    @ResponseBody
    public ModelAndView detail(HttpServletRequest request, String tranId) {
        System.out.println("-----------执行detail方法");
        ModelAndView modelAndView = new ModelAndView();
        Tran tran = tranService.detail(tranId);
        /*可能性的查询*/
        String stage = tran.getStage();
        Map<String, String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        String possibility = pmap.get(stage);
        tran.setPossibility(possibility);
        System.out.println("tran:::hhaha::::" + tran);
        modelAndView.addObject("tran", tran);
        modelAndView.setViewName("forward:/workbench/transaction/detail.jsp");
        return modelAndView;
    }

    /*阶段历史列表处理*/
    @RequestMapping("/workbench/transaction/getHistoryList.do")
    @ResponseBody
    public List<TranHistory> getHistoryList(HttpServletRequest request, String tranId) {
        System.out.println("-----------执行getHistoryList方法");
        List<TranHistory> tranHistories = tranService.getTranHistoryList(tranId);
        Map<String, String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        for (TranHistory tranHistory : tranHistories) {
            String stage = tranHistory.getStage();
            String possibility = pmap.get(stage);
            tranHistory.setPossibility(possibility);
            System.out.println("tranHistory::::::" + tranHistory);
        }
        return tranHistories;
    }


    /*客户名字的模糊查询，完成自动补全*/
    @RequestMapping("/workbench/transaction/changeStage.do")
    @ResponseBody
    public Map<String,Object> changeStage(HttpServletRequest request,Tran tran) {
        System.out.println("-----------执行changeStage方法");
        tran.setEditBy(((User) request.getSession().getAttribute("user")).getName());
        tran.setEditTime(DateTimeUtil.getSysTime());
        Map<String, String> pmap = (Map<String, String>) request.getServletContext().getAttribute("pmap");
        String possibility = pmap.get(tran.getStage());
        tran.setPossibility(possibility);
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = tranService.changeStage(tran,createBy,createTime);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        map.put("tran", tran);
        return map;
    }

}
