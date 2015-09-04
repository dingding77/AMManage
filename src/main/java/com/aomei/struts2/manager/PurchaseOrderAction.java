package com.aomei.struts2.manager;

import com.aomei.dao.CompanyInfoDao;
import com.aomei.model.CompanyInfo;
import com.aomei.model.PurchaseOrder;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/16.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager/purchase")
@Component
public class PurchaseOrderAction extends ActionSupport {
    @Getter
    @Setter
    Map<String,Object> dataMap;     //查询条件
    @Getter @Setter
    public int page;               //分页参数(页数)
    @Getter @Setter
    public int rows;               //分页参数(行数)
    @Getter @Setter
    public String ids;             //操作参数 主键
    @Getter @Setter
    public Integer id;             //操作参数 单一主键
    @Getter @Setter
    public PurchaseOrder purchaseOrder;                //实体对象
    @Getter @Setter
    CompanyInfo companyInfo;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    CompanyInfoDao companyInfoDao;

    /********************进入页面***********************/
    @Action(value="add")
    public String toAddPage(){
        companyInfo=companyInfoDao.selectByPrimaryKey(1);
        return SUCCESS;
    }

    @Action(value="update")
    public String toUpdatePage(){
        int rows=purchaseOrderService.updateById(purchaseOrder);
        return SUCCESS;

    }
    @Action(value="show")
    public String toShowPage(){
        this.purchaseOrder=purchaseOrderService.getByPrimaryKey(id);
        return SUCCESS;
    }

    @Action(value="list")
    public String toListPage(){
        return SUCCESS;
    }

    @Action(value = "getListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String getListJson() {
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        if(purchaseOrder==null){
            purchaseOrder=new PurchaseOrder();
        }
        try{
            dataMap.put("purchaseOrder",purchaseOrder);
            dataMap.put("limitStart",(page-1)*rows);
            dataMap.put("limitEnd",rows);
            List<PurchaseOrder> list=purchaseOrderService.selectPages(dataMap);
            log.info("获取采购单第【{}】页数据，每页显示【{}】条数据",page,rows);
            int total=purchaseOrderService.selectCount(dataMap);
            dataMap.put("rows",list);
            dataMap.put("total", total);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**********************保存**********************/

    @Action(value = "addSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String addSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try {
            this.purchaseOrderService.add(purchaseOrder);
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "editSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editSave(){
        try {
            this.purchaseOrderService.updateById(purchaseOrder);
        }catch (Exception e){
            dataMap.put("errorMsg","修改失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }


    /***
     * 删除数据
     * @return
     */
    @Action(value = "delete", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String orderDelete(){
        try{
            if(dataMap==null){
                dataMap=new HashMap<String, Object>();
            }
            String [] strs=ids.split(",");
            Integer [] ids=new Integer[strs.length];
            for(int i=0;i<strs.length;i++){
                ids[i]=Integer.parseInt(strs[i]);
            }

            if(ids!=null&&ids.length>0){
                int result=purchaseOrderService.deleteByIds(ids);
                if(result<=0){
                    dataMap.put("errorMsg","删除失败");
                }else{
                    dataMap.put("success",SUCCESS);
                }
            }else{
                dataMap.put("errorMsg","删除失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","删除失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }

}
