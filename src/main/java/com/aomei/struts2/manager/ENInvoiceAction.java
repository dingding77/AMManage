package com.aomei.struts2.manager;

import com.aomei.dao.EnCommercialInvoiceDao;
import com.aomei.dao.EnciOrderDao;
import com.aomei.model.EnCommercialInvoice;
import com.aomei.model.EnciOrder;
import com.aomei.util.FreeMakerStreamUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
public class ENInvoiceAction extends ActionSupport {
    @Getter @Setter
    Map<String,Object> dataMap;
    @Getter @Setter
    EnCommercialInvoice enCommercialInvoice;
    @Autowired
    EnCommercialInvoiceDao enCommercialInvoiceDao;
    @Autowired
    EnciOrderDao enciOrderDao;
    @Getter @Setter
    private int page;
    @Getter @Setter
    private int rows;
    @Getter @Setter
    private String ids;
    @Getter @Setter
    private Integer id;

    /******************************以下为商业发票处理方法******************************************/

    /***
     * 获取商业发票列表
     * @return
     */
    @Action(value = "getInvoiceEnListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String getListJson(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        dataMap.put("limitStart",(page-1)*rows);
        dataMap.put("limitEnd",rows);
        List<EnCommercialInvoice> list=enCommercialInvoiceDao.selectPages(dataMap);
        int total=enCommercialInvoiceDao.selectCount(dataMap);
        log.info("获取商业合同第【{}】页数据，每页显示【{}】条数据",page,rows);

        dataMap.put("rows",list);
        dataMap.put("total", total);
        return SUCCESS;
    }

    /**
     *新增保存方法
     * @return
     */
    @Action(value = "enInvoiceAddSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String enInvoiceAddSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=enCommercialInvoiceDao.insertSelective(enCommercialInvoice);
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
    @Action("invoice-en-edit")
    public String toEditPage(){
        if(id!=null){
            enCommercialInvoice=enCommercialInvoiceDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }
    /**
     * 进入显示页面
     * @return
     */
    @Action("invoice-en-show")
    public String toShowPage(){
        if(id!=null){
            enCommercialInvoice=enCommercialInvoiceDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }

    /***
     * 编辑保存处理
     * @return
     */
    @Action(value = "enInvoiceEditSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=enCommercialInvoiceDao.updateByPrimaryKeySelective(enCommercialInvoice);
            List<EnciOrder> list=enCommercialInvoice.getEnciOrders();
            if(list!=null&&list.size()>0){
                result=enciOrderDao.batchUpdate(list);
            }
            if(result<=0){
                dataMap.put("errorMsg","修改失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","修改失败");
           log.error("修改商业发票信息失败{}",e);
        }
        return SUCCESS;
    }

    /***
     * 删除数据
     * @return
     */
    @Action(value = "enInvoice-delete", results = { @Result(name = "success", type = "json", params = {
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
                int result=enCommercialInvoiceDao.deleteByPrimaryKeyArray(ids);
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
    @Action(value = "enInvoiceAction-exportWord",
            results = { @Result(name = "success", type = "stream")
            })
    public void  excel(){
        if(id!=null){
            enCommercialInvoice=enCommercialInvoiceDao.selectByPrimaryKey(id);
            List<EnciOrder> list=enCommercialInvoice.getEnciOrders();
            if(list!=null){
                List<EnciOrder> newList=new ArrayList<EnciOrder>();
                for(EnciOrder order:list){
                    if(StringUtils.isNotEmpty(order.getOrderNo())||StringUtils.isNotEmpty(order.getGoodsDesc())||StringUtils.isNotEmpty(order.getPsc())){
                        newList.add(order);
                    }
                }
                enCommercialInvoice.setEnciOrders(newList);
            }
            //模板参数
            Map<String,Object> templateData = new HashMap<String, Object>();
            templateData.put("EnCommercialInvoice", enCommercialInvoice);
            templateData.put("orderList",enCommercialInvoice.getEnciOrders());
            String filedisplay="发票_"+enCommercialInvoice.getId();
            try {
                String templatePath= FreeMakerStreamUtil.templatePath("/template");
                FreeMakerStreamUtil fsu=new FreeMakerStreamUtil(templatePath,"CommercialInvoice.xml",filedisplay,templateData, ServletActionContext.getResponse());
                fsu.createExce();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{

            }
        }

    }


}
