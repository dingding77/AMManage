package com.aomei.struts2.manager;

import com.aomei.dao.CompanyInfoDao;
import com.aomei.dao.PurchaseDetailDao;
import com.aomei.model.CompanyInfo;
import com.aomei.model.PurchaseDetail;
import com.aomei.model.PurchaseOrder;
import com.aomei.service.PurchaseOrderService;
import com.aomei.util.FreeMakerStreamUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    @Autowired
    public PurchaseDetailDao purchaseDetailDao;
    @Getter @Setter
    CompanyInfo companyInfo;
    @Getter @Setter
    String relation;
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

    @Action(value="edit")
    public String toEditPage(){
        try{

            purchaseOrder=purchaseOrderService.getByPrimaryKey(id);
            String extInfo=purchaseOrder.getExtInfo();
            companyInfo=new CompanyInfo();
            if(StringUtils.isNotEmpty(extInfo)){
                JSONObject jsonObject=JSONObject.fromObject(extInfo);
                String supplierName=jsonObject.getString("supplierName");
                if(StringUtils.isNotEmpty(supplierName)){
                    purchaseOrder.setSupplierName(supplierName);
                }
                String supplierContract=jsonObject.getString("supplierContract");
                if(StringUtils.isNotEmpty(supplierContract)){
                    purchaseOrder.setSupplierContract(supplierContract);
                }
                String supplierPhone=jsonObject.getString("supplierPhone");
                if(StringUtils.isNotEmpty(supplierPhone)){
                    purchaseOrder.setSupplierPhone(supplierPhone);
                }
                String companyName=jsonObject.getString("companyName");
                if(StringUtils.isNotEmpty(companyName)){
                    companyInfo.setName(companyName);
                }
                String companyPhone=jsonObject.getString("companyPhone");
                if(StringUtils.isNotEmpty(companyPhone)){
                    companyInfo.setTelephone(companyPhone);
                }
                String companyContract=jsonObject.getString("companyContract");
                if(StringUtils.isNotEmpty(companyContract)){
                    companyInfo.setContract(companyContract);
                }

            }
            log.info("订单{}共包含{}个产品",id,purchaseOrder.getDetailList().size());
        }catch (Exception e){
            log.error("获取数据异常{}",e);
        }
        return SUCCESS;
    }

    @Action(value="show")
    public String toShowPage(){
        return toEditPage();
    }

    @Action(value="list")
    public String toListPage(){
        log.info("purchase/list、relation={}",relation);
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
            log.error("采购单查询异常{}",e);
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
            String extInfo=purchaseOrder.getExtInfo();
            if(StringUtils.isNotEmpty(extInfo)){
                JSONObject jsonObject=JSONObject.fromObject(extInfo);
                String supplierName=jsonObject.getString("supplierName");
                if(StringUtils.isNotEmpty(supplierName)){
                    purchaseOrder.setSupplierName(supplierName);
                }
                String supplierContract=jsonObject.getString("supplierContract");
                if(StringUtils.isNotEmpty(supplierContract)){
                    purchaseOrder.setSupplierContract(supplierContract);
                }
                String supplierPhone=jsonObject.getString("supplierPhone");
                if(StringUtils.isNotEmpty(supplierPhone)){
                    purchaseOrder.setSupplierPhone(supplierPhone);
                }
            }
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
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try {
            String extInfo=purchaseOrder.getExtInfo();
            if(StringUtils.isNotEmpty(extInfo)){
                JSONObject jsonObject=JSONObject.fromObject(extInfo);
                String supplierName=jsonObject.getString("supplierName");
                if(StringUtils.isNotEmpty(supplierName)){
                    purchaseOrder.setSupplierName(supplierName);
                }
                String supplierContract=jsonObject.getString("supplierContract");
                if(StringUtils.isNotEmpty(supplierContract)){
                    purchaseOrder.setSupplierContract(supplierContract);
                }
                String supplierPhone=jsonObject.getString("supplierPhone");
                if(StringUtils.isNotEmpty(supplierPhone)){
                    purchaseOrder.setSupplierPhone(supplierPhone);
                }
            }
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
    @Action(value = "purchaseOrder-exportWord",
            results = { @Result(name = "success", type = "stream")
            })
    public void  excel(){
        this.purchaseOrder=this.purchaseOrderService.getByPrimaryKey(this.purchaseOrder.getId());
        //模板参数
        Map<String,Object> templateData = new HashMap<String, Object>();
        templateData.put("purchaseOrder", purchaseOrder);
        templateData.put("purchaseDetailList",purchaseOrder.getDetailList());
        String extInfo=purchaseOrder.getExtInfo();
        if(StringUtils.isNotEmpty(extInfo)){
            JSONObject jsonObject=JSONObject.fromObject(extInfo);
            String supplierName=jsonObject.getString("supplierName");
            if(StringUtils.isNotEmpty(supplierName)){
                purchaseOrder.setSupplierName(supplierName);
            }
            String supplierContract=jsonObject.getString("supplierContract");
            if(StringUtils.isNotEmpty(supplierContract)){
                purchaseOrder.setSupplierContract(supplierContract);
            }
            String supplierPhone=jsonObject.getString("supplierPhone");
            if(StringUtils.isNotEmpty(supplierPhone)){
                purchaseOrder.setSupplierPhone(supplierPhone);
            }
            String companyName=jsonObject.getString("companyName");
            if(StringUtils.isNotEmpty(companyName)){
                templateData.put("companyName",companyName);
            }
            String companyPhone=jsonObject.getString("companyPhone");
            if(StringUtils.isNotEmpty(companyPhone)){
                templateData.put("companyPhone",companyPhone);
            }
            String companyContract=jsonObject.getString("companyContract");
            if(StringUtils.isNotEmpty(companyContract)){
                templateData.put("companyContract",companyContract);
            }
            String companyDate=jsonObject.getString("companyDate");
            if(StringUtils.isNotEmpty(companyDate)){
                templateData.put("companyDate",companyDate);
            }
        }
        List<PurchaseDetail> detailList=purchaseOrder.getDetailList();
        int totalNum=0;
        BigDecimal totalAmount=new BigDecimal("0.0");
        if(detailList!=null){
            for(PurchaseDetail detail:detailList){
                totalNum=totalNum+detail.getBuyNum();
                totalAmount= totalAmount.add(new BigDecimal(detail.getTotalAmt()));
            }
        }
        templateData.put("totalAmount",totalAmount.doubleValue());
        templateData.put("totalNum",totalNum);
        String filedisplay="采购单("+this.purchaseOrder.getPurchaseNo()+")";
        try {
            String templatePath= FreeMakerStreamUtil.templatePath("/template");
            FreeMakerStreamUtil fsu=new FreeMakerStreamUtil(templatePath,"PurchaseOrder.xml",filedisplay,templateData, ServletActionContext.getResponse());
            fsu.createExce();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{

        }
    }
}
