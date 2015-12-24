package com.aomei.dao.impl;

import com.aomei.dao.EnCommercialInvoiceDao;
import com.aomei.dao.EnciOrderDao;
import com.aomei.dao.ManufactureOrderDao;
import com.aomei.dao.PurchaseOrderDao;
import com.aomei.model.EnCommercialInvoice;
import com.aomei.model.EnciOrder;
import com.aomei.model.ManufactureOrder;
import com.aomei.model.PurchaseOrder;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */
@Repository("enCommercialInvoiceDao")
public class EnCommercialInvoiceDaoImpl extends MBaseDaoImpl<EnCommercialInvoice> implements EnCommercialInvoiceDao {
    @Autowired
    private EnciOrderDao enciOrderDao;
    @Autowired
    private PurchaseOrderDao purchaseOrderDao;
    @Autowired
    private ManufactureOrderDao manufactureOrderDao;
    @Override
    public int insertSelective(EnCommercialInvoice record) {
        SqlSession session=getSqlSession();
        int result=session.insert(getClassName()+".insertSelective",record);
        List<EnciOrder> list=record.getEnciOrders();
        if(result>0&&list!=null&&list.size()>0){
            for(EnciOrder enciOrder:list){
                enciOrder.setEnciId(record.getId());
            }
            result=enciOrderDao.addEnciOrderBatch(record.getEnciOrders());
        }
        String orderType=record.getRelationOrderType();
        String orderNo=record.getOrderNo();
        //同步完结对应订单 1:生产 2:采购
        if(StringUtils.equals("1",orderType)){
            ManufactureOrder order=new ManufactureOrder();
            order.setProNo(orderNo);
            order.setIsOk("Y");
            //manufactureOrderDao.updateByPrimaryKeySelective(order);
        }else if(StringUtils.equals("2",orderType)){
            PurchaseOrder order=new PurchaseOrder();
            order.setPurchaseNo(orderNo);
            order.setIsOk("Y");
           /// purchaseOrderDao.updateByPrimaryKeySelective(order);
        }
        return result;

    }
    @Override
    public boolean doFinish(Integer id){
        boolean flag=false;
        try{
            EnCommercialInvoice newInvoice=selectByPrimaryKey(id);
            if(newInvoice!=null&& StringUtils.isNotEmpty(newInvoice.getOrderNo())){
                String orderNo=newInvoice.getOrderNo();
                String orderType=newInvoice.getRelationOrderType();
                newInvoice=new EnCommercialInvoice();
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
