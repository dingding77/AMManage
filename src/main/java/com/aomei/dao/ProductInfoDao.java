package com.aomei.dao;

import com.aomei.model.ProductInfo;

public interface ProductInfoDao extends MBaseDao<ProductInfo>{
    public int insertProductNotExists(ProductInfo productInfo) throws Exception;
}