package com.aomei.struts2.manager;

import com.aomei.dao.DeliveryGoodsDao;
import com.aomei.dao.DeliveryNoteDao;
import com.aomei.dao.NumberRecordMapper;
import com.aomei.model.DeliveryGoods;
import com.aomei.model.DeliveryNote;
import com.aomei.model.NumberRecord;
import com.aomei.util.ColumnToPropertyUtil;
import com.aomei.util.FreeMakerStreamUtil;
import com.aomei.util.PrefixUtil;
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

import java.math.BigDecimal;
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
public class ZHInvoiceAction extends ActionSupport {
    @Getter @Setter
    Map<String,Object> dataMap;
    @Getter @Setter
    DeliveryNote deliveryNote;
    @Autowired
    DeliveryNoteDao deliveryNoteDao;
    @Autowired
    DeliveryGoodsDao deliveryGoodsDao;
    @Autowired
    private NumberRecordMapper numberRecordMapper;
    @Getter @Setter
    private int page;
    @Getter @Setter
    private int rows;
    @Getter @Setter
    private String sort;
    @Getter @Setter
    private String order;
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
            dataMap.put("deliveryNote",deliveryNote);
            if(StringUtils.isNotEmpty(sort)){
                dataMap.put("sortName", ColumnToPropertyUtil.propertyToColumn(sort));
                dataMap.put("sortOrder",order);
            }
            List<DeliveryNote> list=deliveryNoteDao.selectPages(dataMap);
            log.info("获取送货单合同第【{}】页数据，每页显示【{}】条数据",page,rows);
            int total=deliveryNoteDao.selectCount(dataMap);
            dataMap.put("rows",list);
            dataMap.put("total", total);
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
            NumberRecord numberRecord=numberRecordMapper.getRecordById();
            int result=-1;
            if(numberRecord!=null){
                deliveryNote.setDeliverNo(PrefixUtil.AM_PREFFIX+"00000"+numberRecord.getAm());
                result=deliveryNoteDao.insertSelective(deliveryNote);
                NumberRecord record=new NumberRecord();
                record.setAm(numberRecord.getAm()+1);
                numberRecordMapper.updateRecord(record);
                if(result<=0){
                    dataMap.put("errorMsg","添加失败");
                }
            }
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            log.error("送货单添加异常{}",e);
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

    @Action(value="doFinish-zh", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String finish(){
        try{
            if(dataMap==null){
                dataMap=new HashMap<String, Object>();
            }
            boolean result=deliveryNoteDao.doFinish(id);
            dataMap.put("success",result);
        }catch (Exception e){
            dataMap.put("success",false);
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
    @Action(value = "zhInvoiceAction-exportWord",
            results = { @Result(name = "success", type = "stream")
            })
    public void  excel(){
        if(id!=null){
            BigDecimal sum=new BigDecimal("0.0");
            deliveryNote=deliveryNoteDao.selectByPrimaryKey(id);
            List<DeliveryGoods> list=deliveryNote.getDeliveryGoodsList();
            if(list!=null){
                List<DeliveryGoods> newList=new ArrayList<DeliveryGoods>();
                for(DeliveryGoods order:list){
                    if(StringUtils.isNotEmpty(order.getContractNo())||StringUtils.isNotEmpty(order.getGoodsNo())||StringUtils.isNotEmpty(order.getGoodsNum()+"")){
                        newList.add(order);
                        if(order.getGoodsAmount()!=null){
                            double val=sum.add(new BigDecimal(order.getGoodsAmount())).doubleValue();
                            sum=new BigDecimal(val);
                        }
                    }
                }
                deliveryNote.setDeliveryGoodsList(newList);
            }

            //模板参数
            Map<String,Object> templateData = new HashMap<String, Object>();
            String sumExp="=SUM(J10:J"+(deliveryNote.getDeliveryGoodsList().size())+")";
            templateData.put("deliveryNote", deliveryNote);
            templateData.put("goodsList",deliveryNote.getDeliveryGoodsList());
            templateData.put("sumExp",sum.doubleValue());
            String filedisplay="送货单";
            try {
                String templatePath= FreeMakerStreamUtil.templatePath("/template");
                FreeMakerStreamUtil fsu=new FreeMakerStreamUtil(templatePath,"DeliveryNote.xml",filedisplay,templateData, ServletActionContext.getResponse());
                fsu.createExce();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally{

            }
        }

    }

}
