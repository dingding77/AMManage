package com.aomei.dao.impl;

import com.aomei.dao.DeliveryGoodsDao;
import com.aomei.dao.DeliveryNoteDao;
import com.aomei.model.DeliveryGoods;
import com.aomei.model.DeliveryNote;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/11.
 */
@Repository("deliveryNoteDao")
public class DeliveryNoteDaoImpl extends MBaseDaoImpl<DeliveryNote> implements DeliveryNoteDao{
    @Autowired
    private DeliveryGoodsDao deliveryGoodsDao;

    @Override
    public int insertSelective(DeliveryNote record) {
        SqlSession session=getSqlSession();
        int result=session.insert(getClassName()+".insertSelective",record);
        List<DeliveryGoods> list=record.getDeliveryGoodsList();
        if(result>0&&list!=null&&list.size()>0){
            for(DeliveryGoods deliveryGoods:list){
                deliveryGoods.setDenoteId(record.getId());
            }
            result=deliveryGoodsDao.addDeliveryGoodsBatch(list);
        }
        return result;

    }
}
