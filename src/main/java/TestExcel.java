import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2015/10/1.
 */
public class TestExcel {
    public static void setRowBorder(HSSFRow row,int maxCellIndex,HSSFCellStyle style){
        for(int i=0;i<maxCellIndex+1;i++){
            HSSFCell cell=row.createCell((short) i);
            if(style!=null){
                cell.setCellStyle(style);
            }
        }
    }
    public static void addImg(HSSFWorkbook wb,HSSFSheet sheet,File imgFile){
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {

            bufferImg = ImageIO.read(imgFile);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            insertImage(wb,patriarch,getImageData(bufferImg),39,49,0,3,0);
            insertImage(wb,patriarch,getImageData(bufferImg),39,49,4,7,0);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }

    //自定义的方法,插入某个图片到指定索引的位置
    private static void insertImage(HSSFWorkbook wb,HSSFPatriarch pa,byte[] data,int beginRow,int endRow,int beginColumn,int endColumn,int index){
        int x1=index*250;
        int y1=0;
        int x2=x1+255;
        int y2=255;
        HSSFClientAnchor anchor = new HSSFClientAnchor(x1,y1,x2,y2,(short)beginColumn,beginRow,(short)endColumn,endRow);
        anchor.setAnchorType(2);
        pa.createPicture(anchor , wb.addPicture(data,HSSFWorkbook.PICTURE_TYPE_JPEG));
    }
    //从图片里面得到字节数组
    private static  byte[] getImageData(BufferedImage bi){
        try{
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            ImageIO.write(bi,"PNG",bout);
            return bout.toByteArray();
        }catch(Exception exe){
            exe.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
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
            HSSFSheet sheet = wb.createSheet("学生表一");
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
            setRowBorder(row4,7,null);
            //第五行
            HSSFRow row5 = sheet.createRow(4);
            setRowBorder(row5,7,null);
            HSSFCell myCell57=row5.createCell((short) 6);
            myCell57.setCellValue("送货单：");

            myCell57.setCellStyle(commonStyle);
            CellRangeAddress cra57=new CellRangeAddress(4, 4, 6, 7);
            sheet.addMergedRegion(cra57);
            //第六行
            HSSFRow row6 = sheet.createRow(5);
            setRowBorder(row6,7,commonStyle);
            HSSFCell myCell61=row6.createCell(0);
            myCell61.setCellValue("客户:");
            myCell61.setCellStyle(commonStyle);
            CellRangeAddress cra62=new CellRangeAddress(5, 5, 1, 3);
            sheet.addMergedRegion(cra62);
            HSSFCell myCell64=row6.createCell(4);
            myCell64.setCellValue("生产单编号:");
            myCell64.setCellStyle(commonStyle);
            CellRangeAddress cra65=new CellRangeAddress(5, 5, 5, 7);
            sheet.addMergedRegion(cra65);


            //第七行
            HSSFRow row7 = sheet.createRow(6);
            setRowBorder(row7,7,commonStyle);
            HSSFCell myCell71=row7.createCell(0);
            myCell71.setCellValue("下单日期:");
            myCell71.setCellStyle(commonStyle);
            CellRangeAddress cra72=new CellRangeAddress(6, 6, 1, 3);
            sheet.addMergedRegion(cra72);
            HSSFCell myCell74=row7.createCell(4);
            myCell74.setCellValue("交货日期:");
            myCell74.setCellStyle(commonStyle);
            CellRangeAddress cra75=new CellRangeAddress(6, 6, 5, 7);
            sheet.addMergedRegion(cra75);

            //第八行
            HSSFRow row8 = sheet.createRow(7);
            setRowBorder(row8,7,commonStyle);
            HSSFCell myCell81=row8.createCell(0);
            myCell81.setCellValue("后道要求：");
            myCell81.setCellStyle(commonStyle);
            CellRangeAddress cra82=new CellRangeAddress(7, 7, 1, 3);
            sheet.addMergedRegion(cra82);
            HSSFCell myCell84=row8.createCell(4);
            myCell84.setCellValue("机台：");
            myCell84.setCellStyle(commonStyle);
            CellRangeAddress cra85=new CellRangeAddress(7, 7, 5, 7);
            sheet.addMergedRegion(cra85);

            //第五行
            HSSFRow row9 = sheet.createRow(8);
            setRowBorder(row9,7,null);

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
            setRowBorder(row10, 7, style10);
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
                setRowBorder(row11,7,commonStyle);
            }
            CellRangeAddress cra110=new CellRangeAddress(10, 14, 0, 0);
            sheet.addMergedRegion(cra110);
            CellRangeAddress cra111=new CellRangeAddress(10, 14, 1, 4);
            sheet.addMergedRegion(cra111);
            CellRangeAddress cra114=new CellRangeAddress(10, 14, 5, 7);
            sheet.addMergedRegion(cra114);
            //第五行
            HSSFRow row16 = sheet.createRow(15);
            setRowBorder(row16,7,commonStyle);
            row16.getCell(0).setCellValue("材料记录：");
            CellRangeAddress cra121=new CellRangeAddress(15, 15, 1, 7);
            sheet.addMergedRegion(cra121);
            //第五行
            HSSFRow row17 = sheet.createRow(16);
            setRowBorder(row17,7,commonStyle);
            row17.getCell(0).setCellValue("返料日期：");
            row17.getCell(2).setCellValue("数量：");
            CellRangeAddress cra171=new CellRangeAddress(16, 16, 3, 7);
            sheet.addMergedRegion(cra171);

            //第五行
            HSSFRow row18 = sheet.createRow(17);
            setRowBorder(row18,7,commonStyle);
            row18.getCell(0).setCellValue("返料日期：");
            row18.getCell(2).setCellValue("数量：");
            CellRangeAddress cra181=new CellRangeAddress(17, 17, 3, 7);
            sheet.addMergedRegion(cra181);

            {
                HSSFRow row19 = sheet.createRow(18);
                setRowBorder(row19, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(18, 18, 0, 7);
                sheet.addMergedRegion(curCra);
            }

            {
                //第20行
                HSSFRow row20 = sheet.createRow(19);
                setRowBorder(row20,7,commonStyle);
                row20.getCell(0).setCellValue("翻单：");
                row20.getCell(2).setCellValue("新版：");
                CellRangeAddress cra201=new CellRangeAddress(19, 19, 3, 7);
                sheet.addMergedRegion(cra201);
            }
            {
                HSSFRow curRow = sheet.createRow(20);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产操作员：");
                CellRangeAddress curCra = new CellRangeAddress(20, 20, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            //第五行
            {
                HSSFRow curRow = sheet.createRow(21);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                CellRangeAddress curCra = new CellRangeAddress(21, 21, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(22);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产日期：");
                CellRangeAddress curCra = new CellRangeAddress(22, 22, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(23);
                setRowBorder(curRow, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(23, 23, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(24);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("剪折操作员：");
                CellRangeAddress curCra = new CellRangeAddress(24, 24, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(25);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                CellRangeAddress curCra = new CellRangeAddress(25, 25, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(26);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("剪折日期：");
                CellRangeAddress curCra = new CellRangeAddress(26, 26, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(27);
                setRowBorder(curRow, 7, style10);
                CellRangeAddress curCra = new CellRangeAddress(27, 27, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(28);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("品检操作员：");
                CellRangeAddress curCra = new CellRangeAddress(28, 28, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(29);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("耗时：");
                CellRangeAddress curCra = new CellRangeAddress(29, 29, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(30);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("包装明细：");
                CellRangeAddress curCra = new CellRangeAddress(30, 30, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(31);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("品检日期：");
                CellRangeAddress curCra = new CellRangeAddress(31, 31, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(32);
                setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(32, 32, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(33);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("备注");
                CellRangeAddress curCra = new CellRangeAddress(33, 33, 1, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(34);
                setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(34, 34, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(35);
                setRowBorder(curRow, 7, null);
                CellRangeAddress curCra = new CellRangeAddress(35, 35, 0, 7);
                sheet.addMergedRegion(curCra);
            }
            {
                HSSFRow curRow = sheet.createRow(36);
                HSSFCellStyle style1 = wb.createCellStyle();
                style1.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式
                style1.setFont(commonFont);
                style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                setRowBorder(curRow, 7, null);
                curRow.getCell(0).setCellValue("产品计划：");
                curRow.getCell(5).setCellValue("产品跟单：");
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
                setRowBorder(curRow, 7, null);
            }

            {
                HSSFRow curRow = sheet.createRow(38);
                setRowBorder(curRow, 7, commonStyle);
                curRow.getCell(0).setCellValue("生产贴样图片");
                curRow.getCell(4).setCellValue("货样贴样");
                CellRangeAddress curCra = new CellRangeAddress(38, 38, 0, 3);
                sheet.addMergedRegion(curCra);
                CellRangeAddress curCra1 = new CellRangeAddress(38, 38, 4, 7);
                sheet.addMergedRegion(curCra1);

                for(int i=39;i<50;i++){
                    HSSFRow hssfRow=sheet.createRow(i);
                    setRowBorder(hssfRow, 7, null);
                }
                CellRangeAddress address1 = new CellRangeAddress(39, 49, 0, 3);
                CellRangeAddress address2 = new CellRangeAddress(39, 49, 4, 7);
                sheet.addMergedRegion(address1);
                sheet.addMergedRegion(address2);
            }
            File imgFile=new File("C:\\Users\\Administrator\\Pictures\\d434eb95c0832776d8d62485d847972e.jpg");
            addImg(wb,sheet,imgFile);
            FileOutputStream fileOut = new FileOutputStream("c://ab.xls");

            wb.write(fileOut);
            fileOut.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
