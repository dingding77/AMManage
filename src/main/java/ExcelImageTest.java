import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/10/2.
 */
public class ExcelImageTest {
    public static void main(String[] args) {
        FileInputStream inputStream=null;
        File file=null;
    try{
        file=new File("C:\\Users\\ADMINI~1\\AppData\\Local\\Temp\\订单.xls");
        inputStream=new FileInputStream(file);
        Workbook book = null;
        try {
            book = new XSSFWorkbook(inputStream);
        } catch (Exception ex) {
            book = new HSSFWorkbook(new FileInputStream(file));
        }
    }catch (Exception e){
        e.printStackTrace();
    }

    }

    public static void ss(HSSFWorkbook wb,HSSFSheet sheet1){
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("C:\\Users\\Administrator\\Pictures\\QQ截图20151002104837.png"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            //452 * 215
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,255,(short) 0, 39, (short) 3, 51);
            anchor.setAnchorType(3);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 1023,255,(short) 4, 39, (short) 7, 51);
            anchor.setAnchorType(3);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }
    public static void smain(String[] args) {
        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("C:\\Users\\Administrator\\Pictures\\QQ截图20151002104837.png"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            //HSSFWorkbook wb = new HSSFWorkbook();
            //HSSFSheet sheet1 = wb.createSheet("test picture");

            FileInputStream finput = new FileInputStream("d://" );

            POIFSFileSystem fs = new POIFSFileSystem( finput );
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            HSSFSheet sheet1=wb.getSheetAt(0);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet1.createDrawingPatriarch();
            //452 * 215
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023,255,(short) 0, 39, (short) 3, 51);
            anchor.setAnchorType(3);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            //anchor主要用于设置图片的属性
            HSSFClientAnchor anchor1 = new HSSFClientAnchor(0, 0, 1023,255,(short) 4, 39, (short) 7, 51);
            anchor.setAnchorType(3);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
            patriarch.createPicture(anchor1, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

            fileOut = new FileOutputStream("D:/测试Excel"+System.currentTimeMillis()+".xls");
            // 写入excel文件
            wb.write(fileOut);
            System.out.println("----Excle文件已生成------");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}