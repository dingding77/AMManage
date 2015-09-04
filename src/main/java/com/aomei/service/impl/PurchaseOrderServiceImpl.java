package com.aomei.service.impl;

import com.aomei.dao.PurchaseDetailDao;
import com.aomei.dao.PurchaseOrderDao;
import com.aomei.model.PurchaseDetail;
import com.aomei.model.PurchaseOrder;
import com.aomei.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
@Service("purchaseOrderService")
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
    @Autowired
    PurchaseOrderDao purchaseOrderDao;

    @Autowired
    PurchaseDetailDao purchaseDetailDao;

    @Override
    public int deleteById(Integer id) {
       return purchaseOrderDao.deleteByPrimaryKey(id);
    }

    @Override
    public int add(PurchaseOrder record) {
        int orderId=purchaseOrderDao.insert(record);
        if(record.getDetailList()!=null){
            for(PurchaseDetail detail:record.getDetailList()){
                detail.setPurchaseId(orderId);
            }
            purchaseDetailDao.insertPurchaseDetailBatch(record.getDetailList());
        }
        return orderId;
    }

    @Override
    public int addSelective(PurchaseOrder record) {
        return purchaseOrderDao.insertSelective(record);
    }

    @Override
    public PurchaseOrder getByPrimaryKey(Integer id) {
        PurchaseOrder purchaseOrder= purchaseOrderDao.selectByPrimaryKey(id);
        if(purchaseOrder!=null){
            List<PurchaseDetail> purchaseDetailList=purchaseDetailDao.selectByPurchaseId(id);
            purchaseOrder.setDetailList(purchaseDetailList);
        }
        return purchaseOrder;
    }

    @Override
    public int updateByIdSelective(PurchaseOrder record) {
        int rows=purchaseOrderDao.updateByPrimaryKeySelective(record);
        purchaseDetailDao.batchUpdate(record.getDetailList());
        return rows;
    }

    @Override
    public int updateById(PurchaseOrder record) {
        int rows=purchaseOrderDao.updateByPrimaryKey(record);
        purchaseDetailDao.batchUpdate(record.getDetailList());
        return rows;
    }

    @Override
    public int deleteByIds(Integer[] ids) {
        return purchaseOrderDao.deleteByIds(ids);
    }

    @Override
   public List<PurchaseOrder> selectPages(Map<String,Object> param){
        return purchaseOrderDao.selectPages(param);
    }

    @Override
    public int selectCount(Map<String, Object> param) {
        return 0;
    }
}
