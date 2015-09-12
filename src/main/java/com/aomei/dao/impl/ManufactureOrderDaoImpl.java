package com.aomei.dao.impl;

import com.aomei.dao.ManufactureOrderDao;
import com.aomei.dao.NumberRecordMapper;
import com.aomei.model.ManufactureOrder;
import com.aomei.model.NumberRecord;
import com.aomei.util.PrefixUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Administrator on 2015/7/8.
 */
@Repository("manufactureOrderDao")
public class ManufactureOrderDaoImpl extends MBaseDaoImpl<ManufactureOrder> implements ManufactureOrderDao {
    @Autowired
    private NumberRecordMapper numberRecordMapper;
    @Override
    public String findMaxOrderNo() throws Exception{
        Object obj=getSqlSession().selectOne("ManufactureOrderMapper.findMaxOrderNo");
        if(obj==null){
            return null;
        }else{
            return obj.toString();
        }
    }
    @Override
    public int addOrder(ManufactureOrder manufactureOrder)throws Exception{
        NumberRecord numberRecord=numberRecordMapper.getRecordById();
        manufactureOrder.setProNo(PrefixUtil.AP_PREFFIX+"00000"+numberRecord.getAp());
        int result=this.insertSelective(manufactureOrder);
        if(result>0){
            numberRecord.setAp(numberRecord.getAp() + 1);
            numberRecordMapper.updateRecord(numberRecord);
        }
        return result;
    }
}
