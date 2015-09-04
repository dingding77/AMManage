package com.aomei.struts2.manager;

import com.aomei.dao.DeliveryGoodsDao;
import com.aomei.dao.DeliveryNoteDao;
import com.aomei.model.DeliveryGoods;
import com.aomei.model.DeliveryNote;
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
 * Created by Administrator on 2015/6/27.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager")
@Component
public class ZHInvoiceAction extends ActionSupport {
    @Getter @Setter
    Map<String,Object> dataMap;
    @Getter @Setter
    DeliveryNote deliveryNote;
    @Autowired
    DeliveryNoteDao deliveryNoteDao;
    @Autowired
    DeliveryGoodsDao deliveryGoodsDao;
    @Getter @Setter
    private int page;
    @Getter @Setter
    private int rows;
    @Getter @Setter
    private String ids;
    @Getter @Setter
    private Integer id;

    /******************************以下为送货单发票处理方法******************************************/

    /***
     * 获取送货单发票列表
     * @return
     */
    @Action(value = "getInvoiceZhListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String getListJson(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            dataMap.put("limitStart",(page-1)*rows);
            dataMap.put("limitEnd",rows);
            List<DeliveryNote> list=deliveryNoteDao.selectPages(dataMap);
            log.info("获取送货单合同第【{}】页数据，每页显示【{}】条数据",page,rows);

            dataMap.put("rows",list);
            dataMap.put("total", 8);
        }catch (Exception e){
            e.printStackTrace();
            log.info("查询送货单列表异常={}",e);
        }
        return SUCCESS;
    }

    /**
     *新增保存方法
     * @return
     */
    @Action(value = "zhInvoiceAddSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String zhInvoiceAddSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=deliveryNoteDao.insertSelective(deliveryNote);
            if(result<=0){
                dataMap.put("errorMsg","添加失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /**
     * 进入修改页面
     * @return
     */
    @Action("invoice-zh-edit")
    public String toEditPage(){
        if(id!=null){
            deliveryNote=deliveryNoteDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }
    /**
     * 进入显示页面
     * @return
     */
    @Action("invoice-zh-show")
    public String toShowPage(){
        if(id!=null){
            deliveryNote=deliveryNoteDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }

    /***
     * 编辑保存处理
     * @return
     */
    @Action(value = "zhInvoiceEditSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=deliveryNoteDao.updateByPrimaryKeySelective(deliveryNote);
            List<DeliveryGoods> goods=deliveryNote.getDeliveryGoodsList();
            if(goods!=null&&goods.size()>0){
                result=deliveryGoodsDao.batchUpdate(goods);
            }
            if(result<0){
                dataMap.put("errorMsg","修改失败");
            }
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
    @Action(value = "zhInvoice-delete", results = { @Result(name = "success", type = "json", params = {
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
                int result=deliveryNoteDao.deleteByPrimaryKeyArray(ids);
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
