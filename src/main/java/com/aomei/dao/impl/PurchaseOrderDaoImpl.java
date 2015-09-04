package com.aomei.dao.impl;

import com.aomei.dao.PurchaseOrderDao;
import com.aomei.model.PurchaseOrder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
@Repository("purchaseOrderDao")
public class PurchaseOrderDaoImpl extends MBaseDaoImpl<PurchaseOrder> implements PurchaseOrderDao {
    @Override
    public int deleteByIds(Integer [] ids){
        int rows=getSqlSession().delete("PurchaseOrderMapper.deleteByPrimaryKeyArray",ids);
        return rows;
    }

    @Override
    public int selectCount(Map<String, Object> param) {
        int totalCount=(Integer)getSqlSession().selectOne("PurchaseOrderMapper.selectCount",param);
        return totalCount;
    }
}
