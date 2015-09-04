package com.aomei.dao.impl;

import com.aomei.dao.ManufactureOrderDao;
import com.aomei.model.ManufactureOrder;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Administrator on 2015/7/8.
 */
@Repository("manufactureOrderDao")
public class ManufactureOrderDaoImpl extends MBaseDaoImpl<ManufactureOrder> implements ManufactureOrderDao {
    public String findMaxOrderNo() throws Exception{
        Object obj=getSqlSession().selectOne("ManufactureOrderMapper.findMaxOrderNo");
        if(obj==null){
            return null;
        }else{
            return obj.toString();
        }
    }
}
