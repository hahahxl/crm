package com.bytedance.crm.workbench.service.impl;

import com.bytedance.crm.utils.DateTimeUtil;
import com.bytedance.crm.utils.UUIDUtil;
import com.bytedance.crm.workbench.dao.*;
import com.bytedance.crm.workbench.domain.*;
import com.bytedance.crm.workbench.service.ClueService;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hxl on 2020/8/18.
 */
@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueDao clueDao = null;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao = null;
    @Autowired
    private CustomerDao customerDao = null;
    @Autowired
    private ContactsDao contactsDao = null;
    @Autowired
    private TranDao tranDao = null;
    @Autowired
    private ClueRemarkDao clueRemarkDao = null;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao = null;
    @Autowired
    private CustomerRemarkDao customerRemarkDao = null;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao = null;
    @Autowired
    private TranHistoryDao tranHistoryDao = null;

    @Override
    public Map<String, Object> save(Clue clue) {
        boolean flag = true;
        Map<String, Object> map = new HashMap<>();
        int num = clueDao.save(clue);
        if (num <= 0) {
            flag = false;
        }

        map.put("success", flag);
        return map;
    }

    @Override
    public Map<String, Object> pageListByCondition(Clue clue) {
        Map<String, Object> map = new HashMap<>();
        List<Clue> clues = clueDao.clueListByCondition(clue);
        int total = clueDao.total(clue);
        map.put("dataList", clues);
        map.put("total", total);
        return map;
    }

    @Override
    public Clue detailById(String id) {
        Clue clue = clueDao.getClueById(id);
        return clue;
    }

    @Override
    public boolean unbund(String id) {
        boolean flag = true;
        int num = clueActivityRelationDao.unbund(id);
        if (num <= 0) {
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean bund(String clueId, String[] aids) {
        boolean flag = true;
        for (String aid : aids) {
            String uuid = UUIDUtil.getUUID();
            int num = clueActivityRelationDao.bund(uuid, clueId, aid);
            if (num <= 0) {
                flag = false;
            }
        }

        return flag;
    }


    /*：转换的实现步骤？
            (1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
			(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
			(3) 通过线索对象提取联系人信息，保存联系人
			(4) 线索备注转换到客户备注以及联系人备注
			(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
			(6) 如果有创建交易需求，创建一条交易
			(7) 如果创建了交易，则创建一条该交易下的交易历史
			(8) 删除线索备注
			(9) 删除线索和市场活动的关系
			(10) 删除线索
*/
    @Override
    public boolean convert(String clueId, Tran tran, String createBy) {
        boolean flag = true;
        Clue clue = clueDao.getClueById(clueId);
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByCompany(company);
        if (customer == null) {
            //新建客户
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            int count1 = customerDao.save(customer);
            if (count1 != 1) {
                flag = false;
            }
        }
        /*保存联系人*/
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setBirth("");
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int count2 = contactsDao.save(contacts);
        if (count2 != 1) {
            flag = false;
        }

        /*(4) 线索备注转换到客户备注以及联系人备注*/
        List<ClueRemark> clueRemarks = clueRemarkDao.getClueRemarkListByClueId(clueId);
        for (ClueRemark clueRemark : clueRemarks) {
         /*转移到联系人备注*/
            String noteContent = clueRemark.getNoteContent();
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            int count3 = contactsRemarkDao.save(contactsRemark);
            if (count3 != 1) {
                flag = false;
            }
            /*转移到客户备注*/
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count4 = customerRemarkDao.save(customerRemark);
            if (count4 != 1) {
                flag = false;
            }
        }
        /*(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系*/
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            String activityId = clueActivityRelation.getActivityId();
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            int count5 = contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5 != 1) {
                flag = false;
            }
        }

        /*(6) 如果有创建交易需求，创建一条交易*/
        if (tran != null) {
            /*创建交易*/
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setType("");
            tran.setSource(clue.getSource());
            tran.setContactsId(contacts.getId());
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());
            int count6 = tranDao.save(tran);
            if (count6 != 1) {
                flag = false;
            }
            //   (7)如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(createBy);
            tranHistory.setTranId(tran.getId());
            int count7 = tranHistoryDao.save(tranHistory);
            if (count7 != 1) {
                flag = false;
            }
        }
        /*	(8) 删除线索备注*/
        for (ClueRemark clueRemark : clueRemarks) {
            String id = clueRemark.getId();
            int count8 = clueRemarkDao.delete(id);
            if (count8 != 1) {
                flag = false;
            }
        }
        //  (9) 删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation : clueActivityRelations) {
            String id = clueActivityRelation.getId();
            int count9 = clueActivityRelationDao.delete(id);
            if (count9 != 1) {
                flag = false;
            }
        }
        //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if (count10 != 1) {
            flag = false;
        }
        return flag;
    }
}
