package com.aomei.dao;

import com.aomei.model.EnciOrder;

import java.util.List;

public interface EnciOrderDao extends MBaseDao<EnciOrder>{
    int addEnciOrderBatch(List<EnciOrder> enciOrderList);
    int batchUpdate(List<EnciOrder> enciOrderList);
}
