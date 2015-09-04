package com.aomei.dao;

import com.aomei.model.DeliveryGoods;

import java.util.List;

public interface DeliveryGoodsDao extends MBaseDao<DeliveryGoods>{
    int addDeliveryGoodsBatch(List<DeliveryGoods> enciOrderList);
    int batchUpdate(List<DeliveryGoods> enciOrderList);
}
