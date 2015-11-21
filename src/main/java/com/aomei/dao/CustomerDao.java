package com.aomei.dao;

import com.aomei.model.Customer;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface CustomerDao extends MBaseDao<Customer>{
    /***
     * 根据客户编码获取客户信息
     * @param code
     * @return
     */
    public Customer selectCustomerByCode(String code) throws DataAccessException;
    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<Customer> selectPages(Map<String,Object> map) throws DataAccessException;
}