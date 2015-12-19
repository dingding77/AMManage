package com.aomei.dao;

import com.aomei.model.ManufactureOrderDetail;

public interface ManufactureOrderDetailMapper extends MBaseDao<ManufactureOrderDetail>{
    public ManufactureOrderDetail selectByOrderId(Integer orderId) throws Exception;
}