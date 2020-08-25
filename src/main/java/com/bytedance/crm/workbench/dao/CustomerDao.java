package com.bytedance.crm.workbench.dao;

import com.bytedance.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByCompany(String company);

    int save(Customer customer);
}
