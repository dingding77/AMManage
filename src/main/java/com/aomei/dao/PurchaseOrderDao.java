package com.aomei.dao;

import com.aomei.model.PurchaseOrder;

import java.util.Map;

public interface PurchaseOrderDao extends MBaseDao<PurchaseOrder> {

    int deleteByIds(Integer [] ids);
    int selectCount(Map<String,Object> param);

}