package com.aomei.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2015/7/19.
 */
public class ExcelUtil {
    /**
     * 功能:实现打印工作
     * @param path
     * @date Oct 29, 2008
     * @time 11:40:03 AM
     */
    public static void print(String path){
        ComThread.InitSTA();
        ActiveXComponent xl = new ActiveXComponent("Excel.Application");
        try {
        // System.out.println("version=" + xl.getProperty("Version"));
        //不打开文档
            Dispatch.put(xl, "Visible", new Variant(true));
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
        //打开文档
            Dispatch excel=Dispatch.call(workbooks,"Open",path).toDispatch();
        //开始打印
            Dispatch.get(excel,"PrintOut");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        //始终释放资源
            ComThread.Release();
        }
    }

    public static void main(String[] args) {
        print("d:/测试Excel1444196149652.xls");
    }
    public static void setRowBorder(HSSFRow row,int maxCellIndex,HSSFCellStyle style){
        for(int i=0;i<maxCellIndex+1;i++){
            HSSFCell cell=row.createCell((short) i);
            if(style!=null){
                cell.setCellStyle(style);
            }
        }
    }

    /**
     *
     * @param wb
     * @param sheet
     * @param positions eg:[index][0]=开始行下标、[index][1]=结束行下标、[index][2]=开始列下标、[index][3]=结束列下标
     * @param imgFiles
     */
    public static void addImg(HSSFWorkbook wb,HSSFSheet sheet,int [][] positions,List<File> imgFiles){
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            if(positions!=null&&imgFiles!=null&&positions.length==imgFiles.size()){
                for(int index=0;index<imgFiles.size();index++){
                    bufferImg = ImageIO.read(imgFiles.get(index));
                    insertImage(wb,patriarch,getImageData(bufferImg),positions[index][0],positions[index][1],positions[index][2],positions[index][3],0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }

    //自定义的方法,插入某个图片到指定索引的位置
    public static void insertImage(HSSFWorkbook wb,HSSFPatriarch pa,byte[] data,int beginRow,int endRow,int beginColumn,int endColumn,int index){
        int x1=index*250;
        int y1=0;
        int x2=x1+255;
        int y2=255;
        HSSFClientAnchor anchor = new HSSFClientAnchor(x1,y1,x2,y2,(short)beginColumn,beginRow,(short)endColumn,endRow);
        anchor.setAnchorType(2);
        pa.createPicture(anchor , wb.addPicture(data,HSSFWorkbook.PICTURE_TYPE_JPEG));
    }
    //从图片里面得到字节数组
    public static  byte[] getImageData(BufferedImage bi){
        try{
            ByteArrayOutputStream bout=new ByteArrayOutputStream();
            ImageIO.write(bi,"PNG",bout);
            return bout.toByteArray();
        }catch(Exception exe){
            exe.printStackTrace();
            return null;
        }
    }
}
