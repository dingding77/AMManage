package com.aomei.struts2.manager;

import com.aomei.dao.MBaseDao;
import com.aomei.dao.ProductInfoMapper;
import com.aomei.model.ProductInfo;
import com.aomei.query.ProductInfoQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager/product")
@Component
public class ProductAction extends BaseAction<ProductInfo> {
    @Getter
    private ProductInfo product;
    @Getter @Setter
    private ProductInfoQuery query;
    @Getter @Setter
    String productName;
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Getter @Setter
    List<ProductInfo> list;
    @Override
    public MBaseDao<ProductInfo> getDao() {
        return productInfoMapper;
    }

    public void setProduct(ProductInfo product) {
        super.modelData=product;
        this.product = product;
    }
    @Action(value = "getListJonsBuQuery", results = { @Result(type = "json",params = {"root","list"})})
    public String  getListJonsBuQuery(){
        try{
            initDataMap();
            if(StringUtils.isNotEmpty(productName)){
                list=productInfoMapper.selectLikeByName(productName);
            }else{
                list=productInfoMapper.selectByExample(null);
            }
        }catch (Exception e){
            log.error("查询列表异常={}",e);
        }
        return SUCCESS;
    }
    @Action(value = "getListJonsByCode", results = { @Result(type = "json",params = {"root","product"})})
    public String  getListJonsByCode(){
        try{
            initDataMap();
            if(query!=null&&query.getCode()!=null){
                list=productInfoMapper.selectByExample(query);
                if(list!=null && list.size()>0){
                    product=list.get(0);
                }
            }

        }catch (Exception e){
            log.error("查询列表异常={}",e);
        }
        return SUCCESS;
    }
}
