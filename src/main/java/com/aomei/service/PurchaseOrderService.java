package com.aomei.service;

import com.aomei.model.PurchaseOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
public interface PurchaseOrderService {
    int deleteById(Integer id);

    int add(PurchaseOrder record);

    int addSelective(PurchaseOrder record);

    PurchaseOrder getByPrimaryKey(Integer id);

    int updateByIdSelective(PurchaseOrder record);

    int updateById(PurchaseOrder record);

    int deleteByIds(Integer [] ids);
    List<PurchaseOrder> selectPages(Map<String,Object> param);
    int selectCount(Map<String,Object> param);
}
