package com.aomei.dao;

import com.aomei.model.PurchaseDetail;

import java.util.List;

public interface PurchaseDetailDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseDetail record);
    void insertPurchaseDetailBatch(List<PurchaseDetail> recordList);
    int insertSelective(PurchaseDetail record);

    PurchaseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseDetail record);

    int updateByPrimaryKey(PurchaseDetail record);
    public List<PurchaseDetail> selectByPurchaseId(int purchaseId);

    public void batchUpdate(List<PurchaseDetail> recordList);
}