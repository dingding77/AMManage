package com.aomei.service.impl;

import com.aomei.dao.NumberRecordMapper;
import com.aomei.dao.PurchaseDetailDao;
import com.aomei.dao.PurchaseOrderDao;
import com.aomei.model.NumberRecord;
import com.aomei.model.PurchaseDetail;
import com.aomei.model.PurchaseOrder;
import com.aomei.service.PurchaseOrderService;
import com.aomei.util.PrefixUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private NumberRecordMapper numberRecordMapper;
    @Autowired
    PurchaseDetailDao purchaseDetailDao;

    @Override
    public int deleteById(Integer id) {
       return purchaseOrderDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int add(PurchaseOrder record) {
        NumberRecord numberRecord=numberRecordMapper.getRecordById();
        record.setPurchaseNo(PrefixUtil.AMPO_PREFFIX+"00000"+numberRecord.getAmpo());
        int orderId=purchaseOrderDao.insert(record);
        if(record.getDetailList()!=null){
            for(PurchaseDetail detail:record.getDetailList()){
                detail.setPurchaseId(record.getId());
            }
            purchaseDetailDao.insertPurchaseDetailBatch(record.getDetailList());
        }
        numberRecord.setAmpo(numberRecord.getAmpo()+1);
        numberRecordMapper.updateRecord(numberRecord);
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
    public int updateByIdSelective(PurchaseOrder record) throws Exception{
        int rows=-1;
        try{
            rows=purchaseOrderDao.updateByPrimaryKeySelective(record);
            purchaseDetailDao.batchUpdate(record.getDetailList());
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return rows;
    }

    @Override
    @Transactional
    public int updateById(PurchaseOrder record) {
        int rows=purchaseOrderDao.updateByPrimaryKeySelective(record);
        if(rows>0){
            List<PurchaseDetail> detailList=record.getDetailList();
            if(detailList!=null){
                List<PurchaseDetail> addList=new ArrayList<PurchaseDetail>();
                for(PurchaseDetail detail:detailList){
                    if(detail.getId()==null){
                        detail.setPurchaseId(record.getId());
                        addList.add(detail);
                    }
                }
                detailList.removeAll(addList);
                purchaseDetailDao.batchUpdate(detailList);
                if(addList.size()>0){
                    purchaseDetailDao.insertPurchaseDetailBatch(addList);
                }
            }
        }
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
        return purchaseOrderDao.selectCount(param);
    }
}
