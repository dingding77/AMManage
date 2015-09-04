package com.aomei.dao;

import com.aomei.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao extends MBaseDao<Customer>{
    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<Customer> selectPages(Map<String,Object> map);
}