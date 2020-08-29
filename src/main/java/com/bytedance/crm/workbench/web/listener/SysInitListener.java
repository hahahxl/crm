package com.bytedance.crm.workbench.web.listener;

import com.bytedance.crm.setting.domain.DicValue;
import com.bytedance.crm.setting.servive.DicService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

/**
 * Created by hxl on 2020/8/16.
 */
public class SysInitListener implements ServletContextListener {
    /* @Autowired
     @Qualifier(value = "dicService")
     ;*/
    private DicService dicService = null;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("=============上下文域对象创建了=============");
        System.out.println("服务器缓存处理数据字典开始");
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        dicService = (DicService) ac.getBean("dicService");
        ServletContext application = event.getServletContext();
        Map<String, List<DicValue>> allDic = dicService.getAllDic();
        Set<String> keySet = allDic.keySet();
        for (String key : keySet) {
            List<DicValue> dicValues = allDic.get(key);
            application.setAttribute(key, dicValues);
        }
        System.out.println("服务器缓存处理数据字典结束");




        /*处理可能性字段*/
        /*1、解析properties
        * 2.将解析后的map集合保存到啊application域中*/
        Map<String, String> pmap = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            //阶段
            String key = keys.nextElement();
            //可能性
            String value = resourceBundle.getString(key);
            pmap.put(key, value);
        }
        //将pMap保存到服务器缓存中
        application.setAttribute("pmap", pmap);
    }
}
