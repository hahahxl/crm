package com.bytedance.crm.workbench.web.listener;

import com.bytedance.crm.setting.domain.DicValue;
import com.bytedance.crm.setting.servive.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            for (DicValue dicValue : dicValues) {
                System.out.println(dicValue);
                System.out.println("key:::" + key + "   value::" + dicValue);
            }
            application.setAttribute(key, dicValues);
        }
        System.out.println("服务器缓存处理数据字典结束");
    }
}
