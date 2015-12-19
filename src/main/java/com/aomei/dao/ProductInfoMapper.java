package com.aomei.dao;

import com.aomei.model.ProductInfo;
import com.aomei.query.ProductInfoQuery;

import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo,ProductInfoQuery>{
    public List<ProductInfo> selectLikeByName(String name);
}