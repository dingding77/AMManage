package com.aomei.dao.impl;

import com.aomei.dao.EnciOrderDao;
import com.aomei.model.EnciOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */

@Repository("enciOrderDao")
public class EnciOrderDaoImpl extends MBaseDaoImpl<EnciOrder> implements EnciOrderDao {
    @Override
    public int addEnciOrderBatch(List<EnciOrder> enciOrderList) {

        return getSqlSession().insert(getClassName()+".addEnciOrderBatch",enciOrderList);
    }

    @Override
    public int batchUpdate(List<EnciOrder> enciOrderList) {
        return getSqlSession().update(getClassName()+".batchUpdate",enciOrderList);
    }
}
