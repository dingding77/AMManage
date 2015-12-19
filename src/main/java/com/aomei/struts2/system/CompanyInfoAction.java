package com.aomei.struts2.system;

import com.aomei.dao.CompanyInfoDao;
import com.aomei.model.CompanyInfo;
import com.aomei.service.PurchaseOrderService;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/12.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/system/company")
@Component
public class CompanyInfoAction extends ActionSupport{
    @Getter
    @Setter
    Map<String,Object> dataMap;     //查询条件
    @Getter
    @Setter
    CompanyInfo companyInfo;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    CompanyInfoDao companyInfoDao;
    /********************进入页面***********************/
    @Action(value="show")
    public String toShowPage(){
        companyInfo=companyInfoDao.selectByPrimaryKey(1);
        return SUCCESS;
    }

    @Action(value="edit")
    public String toeditPage(){
        companyInfo=companyInfoDao.selectByPrimaryKey(1);
        return SUCCESS;
    }

    @Action(value = "companyInfoEditSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String companyInfoEditSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            if(companyInfo!=null&&companyInfo.getId()==1){
                int result=companyInfoDao.updateByPrimaryKey(companyInfo);
                if(result<0){
                    dataMap.put("errorMsg","修改失败");
                }
            }
        }catch (Exception e){
            log.error("公司信息修改异常:{}",e);
        }
        return SUCCESS;
    }
}
