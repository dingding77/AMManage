package com.aomei.dao.impl;

import com.aomei.dao.ProductInfoDao;
import com.aomei.model.ProductInfo;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Administrator on 2015/7/8.
 */
@Repository("productInfoDao")
public class ProductInfoDaoImpl extends MBaseDaoImpl<ProductInfo> implements ProductInfoDao{
    public int insertProductNotExists(ProductInfo productInfo) throws Exception{
        int result=getSqlSession().insert(getClassName()+".insertProductNotExists",productInfo);
        return result;
    }
}
