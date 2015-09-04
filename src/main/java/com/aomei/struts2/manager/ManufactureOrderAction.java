package com.aomei.struts2.manager;

import com.aomei.dao.ManufactureOrderDao;
import com.aomei.model.ManufactureOrder;
import com.aomei.util.FreeMakerStreamUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
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
 * Created by Administrator on 2015/7/8.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager")
@Component
public class ManufactureOrderAction extends ActionSupport {
    @Getter
    @Setter
    private Map<String,Object> dataMap;
    @Getter
    @Setter
    private ManufactureOrder manufactureOrder;
    @Getter
    @Setter
    private int page;
    @Getter
    @Setter
    private int rows;
    @Getter
    @Setter
    private String ids;
    @Getter
    @Setter
    private Integer id;
    @Autowired
    private ManufactureOrderDao manufactureOrderDao;
    @Action(value="order-list")
    public String orderList(){
        return SUCCESS;
    }

    @Action(value = "getManufactureOrderListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String getManufactureOrderListJson() {
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        dataMap.put("limitStart",(page-1)*rows);
        dataMap.put("limitEnd",rows);
        List<ManufactureOrder> list=manufactureOrderDao.selectPages(dataMap);
        log.info("获取订单第【{}】页数据，每页显示【{}】条数据",page,rows);

        dataMap.put("rows",list);
        dataMap.put("total", 8);
        return SUCCESS;
    }
    @Action(value = "addSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String addSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=manufactureOrderDao.insertSelective(manufactureOrder);
            if(result!=1){
                dataMap.put("errorMsg","添加失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }
    @Action("order-edit")
    public String toEditPage(){
        if(id!=null){
            manufactureOrder=manufactureOrderDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }
    @Action("order-show")
    public String toShowPage(){
        if(id!=null){
            manufactureOrder=manufactureOrderDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }
    @Action(value = "editSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=manufactureOrderDao.updateByPrimaryKeySelective(manufactureOrder);
            if(result!=1){
                dataMap.put("errorMsg","修改失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","修改失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }
    @Action(value = "order-delete", results = { @Result(name = "success", type = "json", params = {
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
                int result=manufactureOrderDao.deleteByPrimaryKeyArray(ids);
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
    @Action(value = "manufactureOrder-exportWord",
            results = { @Result(name = "success", type = "stream")
            })
    public void  excel(){
        //模板参数
        Map<String,Object> templateData = new HashMap<String, Object>();
        templateData.put("ALIST", "");

        String filedisplay="订单.xls";
        try {
            String templatePath= FreeMakerStreamUtil.templatePath("/template");
            FreeMakerStreamUtil fsu=new FreeMakerStreamUtil(templatePath,"ManufactureOrder.xml",filedisplay,templateData, ServletActionContext.getResponse());
            fsu.createExce();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{

        }
    }
}
