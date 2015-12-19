package com.aomei.system.dao;

import com.aomei.dao.ProductInfoMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.ProductInfo;
import com.aomei.query.ProductInfoQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
@Repository("productInfoMapper")
public class ProductInfoMapperImpl extends BaseMapperImpl<ProductInfo,ProductInfoQuery> implements ProductInfoMapper{
    public List<ProductInfo> selectLikeByName(String name){
        return getSqlSession().selectList(getClassName() + ".selectLikeByName", name);
    }
}
