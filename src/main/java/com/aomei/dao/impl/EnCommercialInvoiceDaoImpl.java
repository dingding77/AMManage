package com.aomei.dao.impl;

import com.aomei.dao.EnCommercialInvoiceDao;
import com.aomei.dao.EnciOrderDao;
import com.aomei.model.EnCommercialInvoice;
import com.aomei.model.EnciOrder;
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
        return result;

    }
}
