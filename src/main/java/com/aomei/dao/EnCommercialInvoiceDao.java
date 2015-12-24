package com.aomei.dao;

import com.aomei.model.EnCommercialInvoice;

public interface EnCommercialInvoiceDao extends MBaseDao<EnCommercialInvoice>{
    /**
     * 完结当前Invoice 同时完结关联订单
     * @param id
     * @return
     */
    public boolean doFinish(Integer id);
}