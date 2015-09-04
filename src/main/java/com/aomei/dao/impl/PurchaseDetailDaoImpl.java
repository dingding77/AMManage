package com.aomei.dao.impl;

import com.aomei.dao.PurchaseDetailDao;
import com.aomei.model.PurchaseDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/8/16.
 */
@Repository("purchaseDetailDao")
public class PurchaseDetailDaoImpl extends MBaseDaoImpl<PurchaseDetail> implements PurchaseDetailDao {

    @Override
    public void insertPurchaseDetailBatch(List<PurchaseDetail> recordList) {
        getSqlSession().insert("PurchaseDetailMapper.insertPurchaseDetailBatch",recordList);
    }
    @Override
    public List<PurchaseDetail> selectByPurchaseId(int purchaseId){
        PurchaseDetail detail=new PurchaseDetail();
        detail.setPurchaseId(purchaseId);
        return getSqlSession().selectList("PurchaseDetailMapper.selectByCondition", detail);
    }

    @Override
    public void batchUpdate(List<PurchaseDetail> recordList) {
        getSqlSession().update("PurchaseDetailMapper.batchUpdate",recordList);
    }
}
