package com.aomei.dao.impl;

import com.aomei.dao.DeliveryGoodsDao;
import com.aomei.dao.DeliveryNoteDao;
import com.aomei.dao.ManufactureOrderDao;
import com.aomei.dao.PurchaseOrderDao;
import com.aomei.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/11.
 */
@Repository("deliveryNoteDao")
public class DeliveryNoteDaoImpl extends MBaseDaoImpl<DeliveryNote> implements DeliveryNoteDao{
    @Autowired
    private DeliveryGoodsDao deliveryGoodsDao;
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private ManufactureOrderDao manufactureOrderDao;
    @Override
    public int insertSelective(DeliveryNote record) {
        SqlSession session=getSqlSession();
        int result=session.insert(getClassName()+".insertSelective",record);
        List<DeliveryGoods> list=record.getDeliveryGoodsList();
        if(result>0&&list!=null&&list.size()>0){
            for(DeliveryGoods deliveryGoods:list){
                deliveryGoods.setDenoteId(record.getId());
            }
            result=deliveryGoodsDao.addDeliveryGoodsBatch(list);
        }
        return result;

    }
    @Override
    public boolean doFinish(Integer id){
        boolean flag=false;
        try{
            DeliveryNote newInvoice=selectByPrimaryKey(id);
            if(newInvoice!=null&& StringUtils.isNotEmpty(newInvoice.getOrderNo())){
                String orderNo=newInvoice.getOrderNo();
                String orderType=newInvoice.getRelationOrderType();
                newInvoice=new DeliveryNote();
                newInvoice.setId(id);
                newInvoice.setIsOk("Y");
                updateByPrimaryKeySelective(newInvoice);
                //同步完结对应订单 1:生产 2:采购
                if(StringUtils.equals("1",orderType)){
                    ManufactureOrder order=new ManufactureOrder();
                    order.setProNo(orderNo);
                    order.setIsOk("Y");
                    manufactureOrderDao.updateByPrimaryKeySelective(order);
                }else if(StringUtils.equals("2",orderType)){
                    PurchaseOrder order=new PurchaseOrder();
                    order.setPurchaseNo(orderNo);
                    order.setIsOk("Y");
                    purchaseOrderDao.updateByPrimaryKeySelective(order);
                }
            }
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
