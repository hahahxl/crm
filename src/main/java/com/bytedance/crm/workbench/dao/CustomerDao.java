package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {

    Customer getCustomerByCompany(String company);

    int save(Customer customer);

    List<Customer> getCustomerName(String name);

    String getIdByName(String customerName);
}
