package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.workbench.dao.CustomerDao;
import com.bytedance.crm.workbench.domain.Customer;
import com.bytedance.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxl on 2020/8/27.
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao = null;


    @Override
    public List<String> getCustomerName(String name) {
        List<Customer> list = customerDao.getCustomerName(name);
        List<String> list1 = new ArrayList<>();
        for (Customer customer : list) {
            String name1 = customer.getName();
            list1.add(name1);
        }
        return list1;
    }
}
