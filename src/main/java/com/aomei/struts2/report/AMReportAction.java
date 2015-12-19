package com.aomei.struts2.report;

import com.aomei.dao.PurchaseDetailDao;
import com.aomei.dao.ReportConfigMapper;
import com.aomei.model.*;
import com.aomei.struts2.manager.ENInvoiceAction;
import com.aomei.struts2.manager.ManufactureOrderAction;
import com.aomei.struts2.manager.PurchaseOrderAction;
import com.aomei.struts2.manager.ZHInvoiceAction;
import com.aomei.util.DateUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/24.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/report")
@Component
public class AMReportAction extends ActionSupport {
    @Autowired
    private PurchaseOrderAction purchaseOrderAction;//采购
    @Autowired
    private ManufactureOrderAction manufactureOrderAction;//生产单
    @Autowired
    private ENInvoiceAction enInvoiceAction;//商业发票
    @Autowired
    private ZHInvoiceAction zhInvoiceAction;//送货单
    @Autowired
    private ReportConfigMapper reportConfigMapper;

    @Getter
    @Setter
    Map<String,Object> dataMap;     //查询条件
    @Getter @Setter
    public int page;               //分页参数(页数)
    @Getter @Setter
    public int rows;               //分页参数(行数)
    @Getter
    @Setter
    private PurchaseOrder purchaseOrder;
    @Getter
    @Setter
    private ManufactureOrder manufactureOrder;
    @Getter
    @Setter
    EnCommercialInvoice enCommercialInvoice;
    @Getter
    @Setter
    DeliveryNote deliveryNote;
    @Getter
    @Setter
    PurchaseDetailDao purchaseDetailDao;
    //获取采购单列表信息
    @Action(value = "getPurchaseListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String purchaseList(){
        purchaseOrderAction.setPage(this.page);
        purchaseOrderAction.setRows(this.rows);
        purchaseOrderAction.setPurchaseOrder(this.purchaseOrder);
        String result=purchaseOrderAction.getListJson();
        dataMap=purchaseOrderAction.getDataMap();
        return result;
    }
    //导出采购单信息
    @Action(value = "exportPurchaseData",results = { @Result(name = "success", type = "stream")})
    public void reportPurchase(){
        ////1:生产单 2:采购单 3:商业发票 4:送货单
        ReportConfig query=new ReportConfig();
        query.setFormType(ReportFormTypeEnum.REPORT_FORM_2.getCode());
        List<ReportConfig> filedList=reportConfigMapper.selectByExample(query);


        purchaseOrderAction.setPage(0);
        purchaseOrderAction.setRows(0);
        purchaseOrderAction.setPurchaseOrder(this.purchaseOrder);
        String result=purchaseOrderAction.getListJson();
        log.info("reportPurchase 获取数据result={}",result);
        dataMap=purchaseOrderAction.getDataMap();
        //需要导出的数据信息
        List<PurchaseOrder> list=(List<PurchaseOrder>)dataMap.get("rows");

        String [] titles=new String[filedList.size()];
        for(int i=0;i<filedList.size();i++){
            titles[i]=filedList.get(i).getShowTitle();
        }
        HSSFWorkbook wb=this.initTitle("采购单数据",titles);
        HSSFSheet sheet=wb.getSheetAt(0);
        if(list!=null&&list.size()>0){
            for(int i = 0; i < list.size(); i++){
                //获取采购单
                PurchaseOrder purchaseOrder=list.get(i);
                Field parentFields[]=purchaseOrder.getClass().getDeclaredFields();//获得一级对象所有属性
                fullInData(parentFields,purchaseOrder.getDetailList(),sheet,filedList,purchaseOrder);
            }
        }
        this.responseUI(wb,"导出的采购单数据");
    }

    /**
     * 根据属性名称获取属性值
     * @param fields
     * @param attrName
     * @return
     */
    public String getAttrValByName(Field fields[],String attrName,Object obj){
        String attrVal="";
        Field field=null;
        for (int i = 0; i < fields.length; i++) {
            try{
                field=fields[i];
                if(StringUtils.equals(attrName,field.getName())){
                    field.setAccessible(true);//修改访问权限
                    if(field.get(obj)!=null){
                        if(field.get(obj) instanceof Date){
                            attrVal=DateUtil.getUserDate("yyyy-MM-dd");
                        }else{
                            attrVal=field.get(obj).toString();
                        }
                        break;
                    }else{
                        break;
                    }

                }
            }catch (Exception e){
                log.error("获取属性{}异常{}",attrName,e);
            }
        }
        return attrVal;
    }
    //获取生产单列表信息
    @Action(value = "getProductListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
         public String productList(){
        manufactureOrderAction.setPage(this.page);
        manufactureOrderAction.setRows(this.rows);
        manufactureOrderAction.setManufactureOrder(this.manufactureOrder);
        String result=manufactureOrderAction.getManufactureOrderListJson();
        dataMap=manufactureOrderAction.getDataMap();
        return result;
    }

    //导出生产单信息
    @Action(value = "exportProductData",results = { @Result(name = "success", type = "stream")})
    public String reportProduct(){
        manufactureOrderAction.setPage(0);
        manufactureOrderAction.setRows(0);
        manufactureOrderAction.setManufactureOrder(this.manufactureOrder);
        String result=manufactureOrderAction.getManufactureOrderListJson();
        dataMap=manufactureOrderAction.getDataMap();
        ////1:生产单 2:采购单 3:商业发票 4:送货单
        ReportConfig query=new ReportConfig();
        query.setFormType(ReportFormTypeEnum.REPORT_FORM_1.getCode());
        List<ReportConfig> filedList=reportConfigMapper.selectByExample(query);

        //需要导出的数据信息
        List<ManufactureOrder> list=(List<ManufactureOrder>)dataMap.get("rows");

        String [] titles=new String[filedList.size()];
        for(int i=0;i<filedList.size();i++){
            titles[i]=filedList.get(i).getShowTitle();
        }
        HSSFWorkbook wb=this.initTitle("生产单数据",titles);
        HSSFSheet sheet=wb.getSheetAt(0);
        if(list!=null&&list.size()>0){
            for(int i = 0; i < list.size(); i++){
                //获取采购单
                ManufactureOrder order=list.get(i);
                Field parentFields[]=order.getClass().getDeclaredFields();//获得一级对象所有属性
                fullInData(parentFields,order.getDetailList(),sheet,filedList,order);
            }
        }
        this.responseUI(wb,"导出的生产单数据");
        return result;
    }

    //获取商业发票列表信息
    @Action(value = "getDeliveryListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String deliveryList(){
        enInvoiceAction.setPage(this.page);
        enInvoiceAction.setRows(this.rows);
        enInvoiceAction.setEnCommercialInvoice(this.enCommercialInvoice);
        String result=enInvoiceAction.getListJson();
        dataMap=enInvoiceAction.getDataMap();
        return result;
    }
    //导出商业发票信息
    @Action(value = "exportDeliveryData",results = { @Result(name = "success", type = "stream")})
    public String reportDelivery(){
        //1:生产单 2:采购单 3:商业发票 4:送货单
        ReportConfig query=new ReportConfig();
        query.setFormType(ReportFormTypeEnum.REPORT_FORM_3.getCode());
        List<ReportConfig> filedList=reportConfigMapper.selectByExample(query);
        enInvoiceAction.setPage(0);
        enInvoiceAction.setRows(0);
        enInvoiceAction.setEnCommercialInvoice(this.enCommercialInvoice);
        String result=enInvoiceAction.getListJson();
        dataMap=enInvoiceAction.getDataMap();
        List<EnCommercialInvoice> list=(List<EnCommercialInvoice>)dataMap.get("rows");
        String [] titles=new String[filedList.size()];
        for(int i=0;i<filedList.size();i++){
            titles[i]=filedList.get(i).getShowTitle();
        }
        HSSFWorkbook wb=this.initTitle("商业发票数据",titles);
        HSSFSheet sheet=wb.getSheetAt(0);
        if(list!=null&&list.size()>0){
            for(int i = 0; i < list.size(); i++){
                //获取
                EnCommercialInvoice invoice=list.get(i);
                Field parentFields[]=list.get(i).getClass().getDeclaredFields();//获得一级对象所有属性
                fullInData(parentFields,invoice.getEnciOrders(),sheet,filedList,invoice);
            }
        }
        this.responseUI(wb,"导出的商业发票数据");
        return result;
    }

    //获取送货单列表信息
    @Action(value = "getInvoiceListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String invoiceList(){
        zhInvoiceAction.setPage(this.page);
        zhInvoiceAction.setRows(this.rows);
        zhInvoiceAction.setDeliveryNote(this.deliveryNote);
        String result=zhInvoiceAction.getListJson();
        dataMap=zhInvoiceAction.getDataMap();
        return result;
    }

    //导出送货单信息
    @Action(value = "exportInvoiceData",results = { @Result(name = "success", type = "stream")})
    public String reportInvoice(){
        //1:生产单 2:采购单 3:商业发票 4:送货单
        ReportConfig query=new ReportConfig();
        query.setFormType(ReportFormTypeEnum.REPORT_FORM_4.getCode());
        List<ReportConfig> filedList=reportConfigMapper.selectByExample(query);
        zhInvoiceAction.setPage(0);
        zhInvoiceAction.setRows(0);
        zhInvoiceAction.setDeliveryNote(this.deliveryNote);
        String result=zhInvoiceAction.getListJson();
        dataMap=zhInvoiceAction.getDataMap();
        List<DeliveryNote> list=(List<DeliveryNote>)dataMap.get("rows");
        String [] titles=new String[filedList.size()];
        for(int i=0;i<filedList.size();i++){
            titles[i]=filedList.get(i).getShowTitle();
        }
        HSSFWorkbook wb=this.initTitle("送货单数据",titles);
        HSSFSheet sheet=wb.getSheetAt(0);
        //遍历数据
        if(list!=null&&list.size()>0){
            for(int i = 0; i < list.size(); i++){
                //获取
                DeliveryNote invoice=list.get(i);
                Field parentFields[]=list.get(i).getClass().getDeclaredFields();//获得一级对象所有属性
                fullInData(parentFields,invoice.getDeliveryGoodsList(),sheet,filedList,invoice);
            }
        }
        this.responseUI(wb,"导出的送货数据");
        return result;
    }

    /**
     * 初始化电子表格信息
     * @param sheetName
     * @param titles
     * @return
     */
    private HSSFWorkbook initTitle(String sheetName,String [] titles){
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFFont font = wb.createFont();
        font.setFontName("仿宋_GB2312");
        font.setFontHeightInPoints((short) 13);//设置字体大小
        style.setFont(font);

        //设置列宽
        for(int i=0;i<titles.length;i++){
            sheet.setColumnWidth(i, 14 * 256);
        }
        //设置标题
        for(int i=0;i<titles.length;i++){
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
        return wb;
    }
    /**
     * 响应到用户界面
     * @param wb Excel电子薄 对象
     * @param excelName 导出的Excel名称
     */
    private void responseUI(HSSFWorkbook wb,String excelName){
        // 第六步，将文件存到指定位置
        HttpServletResponse response= ServletActionContext.getResponse();
        try
        {
            response.reset();// 清空输出流
            String filename = java.net.URLEncoder.encode(excelName, "utf-8")
                    + ".xls"; // 可设置中文名称
            response.setHeader("Content-disposition", "attachment; filename="
                    + filename);// 设定输出文件头
            response.setContentType("application/msword;charset=UTF-8"); // 定义输出类型

            OutputStream outputStream = response.getOutputStream();

            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e)
        {
            try{
                response.getWriter().write("导出数据异常");
            }catch (Exception ep){

            }
        }
    }

    /**
     * 填充数据
     * @param parentFields 父级 field
     * @param subList   子集合
     * @param sheet
     * @param filedList 配置字段集合
     */
    public void fullInData(Field parentFields[],List subList,HSSFSheet sheet,List<ReportConfig> filedList,Object parentObj){
        //遍历数据
        int initRowIndex=0;
        Row row=null;

        //判断是否存在第二级
        if(subList!=null&&subList.size()>0){
            for(Object detail:subList){
                //创建行
                row = sheet.createRow(initRowIndex + 1);
                Field childFields[]=detail.getClass().getDeclaredFields();//获得二级对象所有属性

                for(int filedIndex=0;filedIndex<filedList.size();filedIndex++){
                    ReportConfig curConfig=filedList.get(filedIndex);
                    //一级
                    if(StringUtils.equals(curConfig.getLevel(),"1")){
                        String curVal=this.getAttrValByName(parentFields,filedList.get(filedIndex).getAttrAme(),parentObj);
                        row.createCell((short) filedIndex).setCellValue(curVal);
                    }else{//二级
                        String curVal=this.getAttrValByName(childFields,filedList.get(filedIndex).getAttrAme(),detail);
                        row.createCell((short) filedIndex).setCellValue(curVal);
                    }
                }
                initRowIndex=initRowIndex+1;
            }
        }else{
            //创建行
            row = sheet.createRow(initRowIndex + 1);
            for(int filedIndex=0;filedIndex<filedList.size();filedIndex++){
                String curVal=this.getAttrValByName(parentFields,filedList.get(filedIndex).getAttrAme(),parentObj);
                row.createCell((short) filedIndex).setCellValue(curVal);
            }
            initRowIndex++;
        }
    }
}
