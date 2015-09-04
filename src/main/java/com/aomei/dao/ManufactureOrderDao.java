package com.aomei.dao;

import com.aomei.model.ManufactureOrder;

import java.util.List;
import java.util.Map;

public interface ManufactureOrderDao extends MBaseDao<ManufactureOrder>{
    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<ManufactureOrder> selectPages(Map<String,Object> map);
    public int deleteByPrimaryKeyArray(Integer[]ids);

    /**
     * 获取最大订单单号
     * @return
     * @throws Exception
     */
    public String findMaxOrderNo() throws Exception;
}