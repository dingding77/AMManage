package com.aomei.struts2.manager;

import com.aomei.dao.ManufactureOrderDao;
import com.aomei.dao.ManufactureOrderDetailMapper;
import com.aomei.model.ManufactureOrder;
import com.aomei.model.ManufactureOrderDetail;
import com.aomei.util.DateUtil;
import com.aomei.util.ExcelUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
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
    @Getter @Setter
    String relation;
    @Autowired
    private ManufactureOrderDao manufactureOrderDao;
    @Autowired
    private ManufactureOrderDetailMapper manufactureOrderDetailMapper;
    private HttpServletRequest request;

    @Action(value="order-list")
    public String orderList(){
        log.info("order-list、relation={}",relation);
        return SUCCESS;
    }

    @Action(value = "getManufactureOrderListJson", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String getManufactureOrderListJson() {
        try{
            if(dataMap==null){
                dataMap=new HashMap<String, Object>();
            }
            dataMap.put("limitStart",(page-1)*rows);
            dataMap.put("limitEnd",rows);
            dataMap.put("manufactureOrder",manufactureOrder);
            List<ManufactureOrder> list=manufactureOrderDao.selectPages(dataMap);
            int total=manufactureOrderDao.selectCount(dataMap);
            log.info("获取订单第【{}】页数据，每页显示【{}】条数据",page,rows);

            dataMap.put("rows",list);
            dataMap.put("total", total);
        }catch (Exception e){
            log.error("查询异常{}",e);
        }

        return SUCCESS;
    }
    @Action(value = "manufactureAddSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String addSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=manufactureOrderDao.addOrder(manufactureOrder);
            if(result!=1){
                dataMap.put("errorMsg","添加失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            log.error("生产单添加失败{}",e);
        }
        return SUCCESS;
    }
    @Action("order-edit")
    public String toEditPage(){
        try{
            if(id!=null){
                manufactureOrder=manufactureOrderDao.selectByPrimaryKey(id);
                ManufactureOrderDetail detail= manufactureOrderDetailMapper.selectByOrderId(id);
                if(detail!=null){
                    List<ManufactureOrderDetail> detailList=new ArrayList<ManufactureOrderDetail>();
                    detailList.add(detail);
                    manufactureOrder.setDetailList(detailList);
                }
            }
        }catch (Exception e){
            log.error("查询异常{}",e);
        }

        return SUCCESS;
    }
    @Action("order-show")
    public String toShowPage(){
        try{
            if(id!=null){
                manufactureOrder=manufactureOrderDao.selectByPrimaryKey(id);
                ManufactureOrderDetail detail= manufactureOrderDetailMapper.selectByOrderId(id);
                if(detail!=null){
                    List<ManufactureOrderDetail> detailList=new ArrayList<ManufactureOrderDetail>();
                    detailList.add(detail);
                    manufactureOrder.setDetailList(detailList);
                }
            }
        }catch (Exception e){
            log.error("查询异常{}",e);
        }
        return SUCCESS;
    }
    @Action(value = "manufactureEditSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        try{
            int result=manufactureOrderDao.updateOrder(manufactureOrder);
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
        request = ServletActionContext.getRequest();
        this.manufactureOrder=this.manufactureOrderDao.selectByPrimaryKey(this.manufactureOrder.getId());
        generatorExcel(this.manufactureOrder);
    }

    public void generatorExcel(ManufactureOrder manufactureOrder) {
        try{

            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //
            //第一行字体
            HSSFFont commonFont=wb.createFont();
            commonFont.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            commonFont.setFontHeightInPoints((short)12);
            commonFont.setFontName("宋体");
            //第一行样式
            HSSFCellStyle commonStyle = wb.createCellStyle();
            commonStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            commonStyle.setFont(commonFont);

            commonStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            commonStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            commonStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            commonStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            //
            //第一行字体
            HSSFFont font=wb.createFont();
            font.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            font.setFontHeightInPoints((short)24);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
            font.setFontName("楷体_GB2312");
            //第一行样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style.setFont(font);

            //第二行字体
            HSSFFont font2=wb.createFont();
            font2.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            font2.setFontHeightInPoints((short)18);
            font2.setFontName("楷体_GB2312");
            //第二行样式
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style2.setFont(font2);

            //第三行字体
            HSSFFont font3=wb.createFont();
            font3.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            font3.setFontHeightInPoints((short)12);
            font3.setFontName("楷体_GB2312");
            //第三行样式
            HSSFCellStyle style3 = wb.createCellStyle();
            style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style3.setFont(font3);

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet("生产单("+manufactureOrder.getProNo()+")");
            for(int i=0;i<8;i++){
                sheet.setColumnWidth(i, 14 * 256);
            }
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);




            //第一行第一个单元格
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue("上 海 傲 美 实 业 有 限 公 司");
            cell.setCellStyle(style);
            CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 7);
            sheet.addMergedRegion(cra);
            //第二行第一个单元格
            HSSFRow row2 = sheet.createRow(1);
            HSSFCell cell2 = row2.createCell((short) 0);
            cell2.setCellValue("生产单");
            font2.setFontHeightInPoints((short)18);
            style2.setFont(font2);
            cell2.setCellStyle(style2);
            CellRangeAddress cra2=new CellRangeAddress(1, 1, 0, 7);
            sheet.addMergedRegion(cra2);

            //第三个单元格
            HSSFRow row3 = sheet.createRow(2);
            HSSFCell cell3 = row3.createCell((short) 0);
            cell3.setCellValue("上海嘉定区南翔镇昌翔路575号2号门2楼 TEL：021-66306672 FAX：021-56065197");
            font3.setFontHeightInPoints((short)12);
            style3.setFont(font3);
            cell3.setCellStyle(style3);
            CellRangeAddress cra3=new CellRangeAddress(2, 2, 0, 7);

            sheet.addMergedRegion(cra3);

            HSSFRow row4 = sheet.createRow(3);
            ExcelUtil.setRowBorder(row4,7,null);
            //第五行
            HSSFRow row5 = sheet.createRow(4);
            ExcelUtil.setRowBorder(row5,7,null);
            HSSFCell myCell57=row5.createCell((short) 6);
            myCell57.setCellValue("送货单：");
            CellRangeAddress cra57=new CellRangeAddress(4, 4, 6, 7);
            sheet.addMergedRegion(cra57);
            //第六行
            HSSFRow row6 = sheet.createRow(5);
            ExcelUtil.setRowBorder(row6,7,commonStyle);
            HSSFCell myCell61=row6.getCell(0);
            myCell61.setCellValue("客户:");
            row6.getCell(1).setCellValue(manufactureOrder.getCstmCode());
            myCell61.setCellStyle(commonStyle);
            CellRangeAddress cra62=new CellRangeAddress(5, 5, 1, 3);
            sheet.addMergedRegion(cra62);
            HSSFCell myCell64=row6.getCell(4);
            myCell64.setCellValue("生产单编号:");
            row6.getCell(5).setCellValue(manufactureOrder.getProNo());
            myCell64.setCellStyle(commonStyle);
            CellRangeAddress cra65=new CellRangeAddress(5, 5, 5, 7);
            sheet.addMergedRegion(cra65);


            //第七行
            HSSFRow row7 = sheet.createRow(6);
            ExcelUtil.setRowBorder(row7,7,commonStyle);
            HSSFCell myCell71=row7.getCell(0);
            myCell71.setCellValue("下单日期:");
            if(manufactureOrder.getOrderDate()!=null){
                row7.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getOrderDate()));
            }
            myCell71.setCellStyle(commonStyle);
            CellRangeAddress cra72=new CellRangeAddress(6, 6, 1, 3);
            sheet.addMergedRegion(cra72);
            HSSFCell myCell74=row7.getCell(4);
            myCell74.setCellValue("交货日期:");
            if(manufactureOrder.getDeliveryDate()!=null){
                row7.getCell(5).setCellValue(DateUtil.dateToStr(manufactureOrder.getDeliveryDate()));
            }

            myCell74.setCellStyle(commonStyle);
            CellRangeAddress cra75=new CellRangeAddress(6, 6, 5, 7);
            sheet.addMergedRegion(cra75);

            //第八行
            HSSFRow row8 = sheet.createRow(7);
            ExcelUtil.setRowBorder(row8,7,commonStyle);
            HSSFCell myCell81=row8.getCell(0);
            myCell81.setCellValue("后道要求：");
            row8.getCell(1).setCellValue(manufactureOrder.getHoudaoRequests());
            myCell81.setCellStyle(commonStyle);
            CellRangeAddress cra82=new CellRangeAddress(7, 7, 1, 3);
            sheet.addMergedRegion(cra82);
            HSSFCell myCell84=row8.getCell(4);
            myCell84.setCellValue("机台：");
            row8.getCell(5).setCellValue(manufactureOrder.getBoard());
            myCell84.setCellStyle(commonStyle);
            CellRangeAddress cra85=new CellRangeAddress(7, 7, 5, 7);
            sheet.addMergedRegion(cra85);

            //第五行
            HSSFRow row9 = sheet.createRow(8);
            ExcelUtil.setRowBorder(row9,7,null);

            //第五行
            HSSFRow row10 = sheet.createRow(9);
            HSSFFont font10=wb.createFont();
            font10.setColor(HSSFColor.BLACK.index);//HSSFColor.VIOLET.index //字体颜色
            font10.setFontHeightInPoints((short)12);
            font10.setFontName("楷体_GB2312");
            font10.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle style10 = wb.createCellStyle();
            style10.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style10.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            style10.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style10.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            style10.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            style10.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            style10.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            style10.setFont(font10);
            ExcelUtil.setRowBorder(row10, 7, style10);
            row10.getCell(0).setCellValue("数量（个）");
            row10.getCell(1).setCellValue("生 产 描 述");
            CellRangeAddress cra101=new CellRangeAddress(9, 9, 1, 4);
            sheet.addMergedRegion(cra101);
            row10.getCell(5).setCellValue("款号");
            CellRangeAddress cra105=new CellRangeAddress(9, 9, 5, 7);
            sheet.addMergedRegion(cra105);

            //第五行
            for(int i=10;i<15;i++){
                HSSFRow row11 = sheet.createRow(i);
                ExcelUtil.setRowBorder(row11,7,commonStyle);
            }
            HSSFRow row11=sheet.getRow(10);
            row11.getCell(0).setCellValue(manufactureOrder.getProNum()==null?"":manufactureOrder.getProNum().toString());
            row11.getCell(1).setCellValue(manufactureOrder.getProDesc());
            row11.getCell(5).setCellValue(manufactureOrder.getStyleNo());
            CellRangeAddress cra110=new CellRangeAddress(10, 14, 0, 0);
            sheet.addMergedRegion(cra110);
            CellRangeAddress cra111=new CellRangeAddress(10, 14, 1, 4);
            sheet.addMergedRegion(cra111);
            CellRangeAddress cra114=new CellRangeAddress(10, 14, 5, 7);
            sheet.addMergedRegion(cra114);
            //第五行
            HSSFRow row16 = sheet.createRow(15);
            ExcelUtil.setRowBorder(row16,7,commonStyle);
            row16.getCell(0).setCellValue("材料记录：");
            CellRangeAddress cra121=new CellRangeAddress(15, 15, 1, 7);
            sheet.addMergedRegion(cra121);
            //第五行
            HSSFRow row17 = sheet.createRow(16);
            ExcelUtil.setRowBorder(row17,7,commonStyle);
            row17.getCell(0).setCellValue("领料日期：");
            if(manufactureOrder.getCallslipDate()!=null){
                row17.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getCallslipDate()));
            }
            row17.getCell(2).setCellValue("材料编号：");
            row17.getCell(3).setCellValue(manufactureOrder.getMaterialNo());
            row17.getCell(4).setCellValue("实需数量：");
            row17.getCell(5).setCellValue(manufactureOrder.getNeedNum()==null?"":manufactureOrder.getNeedNum().toString());
            row17.getCell(6).setCellValue("实领数量：");
            row17.getCell(7).setCellValue(manufactureOrder.getRealNum()==null?"":manufactureOrder.getRealNum().toString());

            //第五行
            HSSFRow row18 = sheet.createRow(17);
            ExcelUtil.setRowBorder(row18,7,commonStyle);
            row18.getCell(0).setCellValue("返料日期：");
            if(manufactureOrder.getRevertDate()!=null){
                row18.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getRevertDate()));
            }
            row18.getCell(2).setCellValue("数量：");
            row18.getCell(3).setCellValue(manufactureOrder.getRevertNum()==null?"":manufactureOrder.getRevertNum().toString());
            CellRangeAddress cra181=new CellRangeAddress(17, 17, 3, 7);
            sheet.addMergedRegion(cra181);

            {
                HSSFRow row19 = sheet.createRow(18);
                ExcelUtil.setRowBorder(row19, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(18, 18, 0, 7);
                sheet.addMergedRegion(curCra);
            }

            {
                //第20行
                HSSFRow row20 = sheet.createRow(19);
                ExcelUtil.setRowBorder(row20,7,commonStyle);
                row20.getCell(0).setCellValue("翻单：");
                row20.getCell(1).setCellValue(manufactureOrder.getRepeatOrder());
                row20.getCell(2).setCellValue("新版：");
                row20.getCell(3).setCellValue(manufactureOrder.getNewEdition());
                CellRangeAddress cra201=new CellRangeAddress(19, 19, 3, 7);
                sheet.addMergedRegion(cra201);
            }
            {
                HSSFRow curRow = sheet.createRow(20);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产操作员：");
                curRow.getCell(1).setCellValue(manufactureOrder.getProOperator());
                CellRangeAddress curCra = new CellRangeAddress(20, 20, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            //第五行
            {
                HSSFRow curRow = sheet.createRow(21);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                curRow.getCell(1).setCellValue(manufactureOrder.getProTimeConsuming());
                CellRangeAddress curCra = new CellRangeAddress(21, 21, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(22);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产日期：");
                if(manufactureOrder.getProDate()!=null){
                    curRow.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getProDate()));
                }
                CellRangeAddress curCra = new CellRangeAddress(22, 22, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(23);
                ExcelUtil.setRowBorder(curRow, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(23, 23, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(24);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("剪折操作员：");
                curRow.getCell(1).setCellValue(manufactureOrder.getFssOperator());
                CellRangeAddress curCra = new CellRangeAddress(24, 24, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(25);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                curRow.getCell(1).setCellValue(manufactureOrder.getFssTimeConsuming());
                CellRangeAddress curCra = new CellRangeAddress(25, 25, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(26);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("剪折日期：");
                if(manufactureOrder.getFssDate()!=null) {
                    curRow.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getFssDate()));
                }
                CellRangeAddress curCra = new CellRangeAddress(26, 26, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(27);
                ExcelUtil.setRowBorder(curRow, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(27, 27, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(28);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("品检操作员：");
                curRow.getCell(1).setCellValue(manufactureOrder.getQcOperator());
                CellRangeAddress curCra = new CellRangeAddress(28, 28, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(29);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                curRow.getCell(1).setCellValue(manufactureOrder.getQcTimeConsuming());
                CellRangeAddress curCra = new CellRangeAddress(29, 29, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(30);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("包装明细：");
                curRow.getCell(1).setCellValue(manufactureOrder.getPackDetail());
                CellRangeAddress curCra = new CellRangeAddress(30, 30, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(31);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("品检日期：");
                if(manufactureOrder.getQcDate()!=null){
                    curRow.getCell(1).setCellValue(DateUtil.dateToStr(manufactureOrder.getQcDate()));
                }
                CellRangeAddress curCra = new CellRangeAddress(31, 31, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(32);
                ExcelUtil.setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(32, 32, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(33);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("备注");
                curRow.getCell(1).setCellValue(manufactureOrder.getRemark());
                CellRangeAddress curCra = new CellRangeAddress(33, 33, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(34);
                ExcelUtil.setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(34, 34, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(35);
                ExcelUtil.setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(35, 35, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(36);
                HSSFCellStyle style1 = wb.createCellStyle();
                style1.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
                style1.setFont(commonFont);
                style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                ExcelUtil.setRowBorder(curRow, 7, null);
                curRow.getCell(0).setCellValue("产品计划："+manufactureOrder.getProPlanning());
                curRow.getCell(5).setCellValue("产品跟单："+manufactureOrder.getProDocumentary());
                for(int i=0;i<3;i++){
                    curRow.getCell(i).setCellStyle(style1);
                }
                CellRangeAddress curCra = new CellRangeAddress(36, 36, 0, 2);
                sheet.addMergedRegion(curCra);
                for(int i=5;i<8;i++){
                    curRow.getCell(i).setCellStyle(style1);
                }
                CellRangeAddress curCra1 = new CellRangeAddress(36, 36, 5, 7);
                sheet.addMergedRegion(curCra1);
            }

            {
                HSSFRow curRow = sheet.createRow(37);
                ExcelUtil.setRowBorder(curRow, 7, null);
            }

            {
                HSSFRow curRow = sheet.createRow(38);
                ExcelUtil.setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产贴样图片");
                curRow.getCell(4).setCellValue("货样贴样");
                CellRangeAddress curCra = new CellRangeAddress(38, 38, 0, 3);
                sheet.addMergedRegion(curCra);
                CellRangeAddress curCra1 = new CellRangeAddress(38, 38, 4, 7);
                sheet.addMergedRegion(curCra1);

                for(int i=39;i<50;i++){
                    HSSFRow hssfRow=sheet.createRow(i);
                    ExcelUtil.setRowBorder(hssfRow, 7, null);
                }
                CellRangeAddress address1 = new CellRangeAddress(39, 49, 0, 3);
                CellRangeAddress address2 = new CellRangeAddress(39, 49, 4, 7);
                sheet.addMergedRegion(address1);
                sheet.addMergedRegion(address2);
            }
            String webRoot = request.getSession().getServletContext()
                    .getRealPath("/");
            List<File> imgFiles=new ArrayList<File>();
            List<Integer[]> listPositions=new ArrayList<Integer[]>();

            //第一个图片
            String proPasteLike=manufactureOrder.getProPasteLike();
            String goodsPasteLike=manufactureOrder.getGoodsPasteLike();
            if(StringUtils.isNotEmpty(proPasteLike)){
                proPasteLike=proPasteLike.substring(proPasteLike.indexOf("upload/"));
                StringBuffer sourcePath = new StringBuffer();
                sourcePath.append(webRoot);
                sourcePath.append(File.separator);
                sourcePath.append(proPasteLike);
                imgFiles.add(new File(sourcePath.toString()));
                //39, 49, 0, 3
                listPositions.add(new Integer[]{39, 49, 0,3});
            }
            if(StringUtils.isNotEmpty(proPasteLike)){
                goodsPasteLike=proPasteLike.substring(proPasteLike.indexOf("upload/"));
                StringBuffer sourcePath = new StringBuffer();
                sourcePath.append(webRoot);
                sourcePath.append(File.separator);
                sourcePath.append(goodsPasteLike);
                imgFiles.add(new File(sourcePath.toString()));
                //39, 49, 4, 7
                listPositions.add(new Integer[]{39, 49, 4, 7});
            }

            int [][] positions=new int[listPositions.size()][4];
            for(int i=0;i<listPositions.size();i++){
                Integer [] pos=listPositions.get(i);
                positions[i][0]=pos[0];
                positions[i][1]=pos[1];
                positions[i][2]=pos[2];
                positions[i][3]=pos[3];
            }
            ExcelUtil.addImg(wb,sheet,positions,imgFiles);
            HttpServletResponse response=ServletActionContext.getResponse();
            OutputStream os = response.getOutputStream();
            response.reset();// 清空输出流
            String filename = java.net.URLEncoder.encode("生产单_"+manufactureOrder.getProNo(), "utf-8")
                    + ".xls"; // 可设置中文名称
            response.setHeader("Content-disposition", "attachment; filename="
                    + filename);// 设定输出文件头
            response.setContentType("application/msword;charset=UTF-8"); // 定义输出类型

            OutputStream outputStream = response.getOutputStream();

            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}
