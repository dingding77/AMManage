package com.aomei.dao.impl;

import com.aomei.dao.DeliveryGoodsDao;
import com.aomei.model.DeliveryGoods;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/11.
 */
@Repository("deliveryGoodsDao")
public class DeliveryGoodsDaoImpl extends MBaseDaoImpl<DeliveryGoods> implements DeliveryGoodsDao {
    @Override
    public int addDeliveryGoodsBatch(List<DeliveryGoods> enciOrderList) {

        return getSqlSession().insert(getClassName()+".addDeliveryGoodsBatch",enciOrderList);
    }

    @Override
    public int batchUpdate(List<DeliveryGoods> enciOrderList) {
        return getSqlSession().update(getClassName()+".batchUpdate",enciOrderList);
    }
}
