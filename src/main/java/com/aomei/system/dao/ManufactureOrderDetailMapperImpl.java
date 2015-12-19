package com.aomei.system.dao;

import com.aomei.dao.ManufactureOrderDetailMapper;
import com.aomei.dao.impl.MBaseDaoImpl;
import com.aomei.model.ManufactureOrderDetail;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/12/5.
 */
@Repository("manufactureOrderDetailMapper")
public class ManufactureOrderDetailMapperImpl extends MBaseDaoImpl<ManufactureOrderDetail> implements ManufactureOrderDetailMapper {
    public ManufactureOrderDetail selectByOrderId(Integer orderId) throws Exception{
        Object obj=getSqlSession().selectOne(getClassName()+".selectByOrderId",orderId);
        if(obj==null){
            return null;
        }else{
            return (ManufactureOrderDetail)obj;
        }
    }
}
