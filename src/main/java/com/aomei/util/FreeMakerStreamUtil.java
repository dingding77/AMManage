package com.aomei.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class FreeMakerStreamUtil {
    @Getter @Setter
    private String templatePath;// 模板文件路径,不包含文件名
    @Getter @Setter
    private String templateFileName;// 模板文件名
    @Getter @Setter
    private String outFileName;
    @Getter @Setter
    private Map<String,Object> dataMap;// 数据源
    @Getter @Setter
    private Configuration configuration;
    @Getter @Setter
    private HttpServletResponse response;
    @Getter @Setter
    private boolean isResponseClient=true;
    @Getter @Setter
    private List<String> imgList;

    public FreeMakerStreamUtil() {

    }

    /**
     *
     * @param templatePath
     * @param templateFileName
     * @param outFileName
     * @param dataMap
     * @param response
     * @throws IOException
     */
    public FreeMakerStreamUtil(String templatePath, String templateFileName,
                               String outFileName, Map<String,Object> dataMap, HttpServletResponse response)
            throws IOException {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");// 模板编码utf-8
        configuration.setClassicCompatible(true);// 向前兼容
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        this.templateFileName = templateFileName;
        this.outFileName = outFileName;
        this.dataMap = dataMap;
        this.templatePath = templatePath;
        this.response = response;
    }

    public void createExce() throws Exception{
        String tmpDir=null;
        Writer out=null;
        try{
            Properties p = System.getProperties();
            String tempPath = p.getProperty("java.io.tmpdir");
            String separator = p.getProperty("file.separator");
            tmpDir=tempPath + separator + outFileName;
            File tempFile = new File(tmpDir);
            out= new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(tempFile), "UTF-8"));
            configuration.getTemplate(templateFileName).process(dataMap, out);
        }catch (Exception e){
            throw  e;
        }finally {
            if(out!=null){
                out.close();
                out=null;
            }
        }
        File outfile=new File(tmpDir);
        if(isResponseClient){
            exportWord(outfile);//弹出excel保存到客户端
        }

    }
    public static String templatePath(String templateDir){
        String classPath=FreeMakerStreamUtil.class.getResource("").getPath();
        String templatePath= classPath.substring(0, classPath.lastIndexOf("/classes/"));
        if(classPath.indexOf("target")>0){
            templatePath=templatePath+"/AMManage/WEB-INF"+templateDir;
        }else{
            templatePath=templatePath+templateDir;
        }

        return templatePath;
    }
    private void writeImg(){
        {
            FileOutputStream fileOut = null;
            BufferedImage bufferImg = null;
            //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
            try {
                org.apache.commons.io.output.ByteArrayOutputStream byteArrayOut = new org.apache.commons.io.output.ByteArrayOutputStream();
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

    public void exportWord(File outfile) throws Exception{
        response.reset();// 清空输出流
        String filename = java.net.URLEncoder.encode(outFileName, "utf-8")
                + ".xls"; // 可设置中文名称
        response.setHeader("Content-disposition", "attachment; filename="
                + filename);// 设定输出文件头
        response.setContentType("application/msword;charset=UTF-8"); // 定义输出类型
        //获取输入流 当前流数据中仅包含字符信息
        FileInputStream inputStream = new FileInputStream(outfile);

        //设置图片 end
        OutputStream outputStream = response.getOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                inputStream); // 缓冲文件输入流
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                outputStream); // 缓冲文件输出流
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    bufferedOutputStream, "UTF-8"));
            byte[] buffer = new byte[10240];//测试
            int i = -1;
            while ((i = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedOutputStream.flush();
            bufferedInputStream.close();
            bufferedOutputStream.close();
            if (outfile != null)
                outfile.delete();
        }
    }

    // 删除文件夹
    // param folderPath 文件夹完整绝对路径
    public void delFolder(String folderPath) {
        try {
            System.out.println("222");
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    // param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获得模版文件
     *
     * @param templateFileName
     * @return
     * @throws IOException
     */
    private Template getTemplate(String templateFileName) throws IOException {
        return configuration.getTemplate(templateFileName);
    }

    /**
     * 填充数据
     *
     * @param strArray
     *            model对象的属性名称
     * @return
     * @throws Exception
     */
    public Map getData(String[] strArray, String className) throws Exception {
        Map dataMap = new HashMap();
        Class<?> demo = Class.forName(className);
        Object obj = demo.newInstance();
        for (int i = 0; i < strArray.length; i++) {
            dataMap.put(strArray[i], getter(obj, upperCaseString(strArray[i])));
        }
        return dataMap;
    }

    /**
     * 获得数据集
     *
     * @param className
     * @param methodName
     * @return
     * @throws Exception
     */
    public List<?> getListData(String className, String methodName, Map map)
            throws Exception {
        Class<?> demo = Class.forName(className);
        Object obj = demo.newInstance();
        Method method = null;
        if (map != null) {
            method = demo.getMethod(methodName, Map.class);
            return (List<?>) method.invoke(obj, map);
        } else {
            method = demo.getMethod(methodName);
            return (List<?>) method.invoke(obj);
        }
    }

    /**
     * 将string的首字母大些
     *
     * @param str
     * @return
     */
    public String upperCaseString(String str) {
        String temp = str.substring(0, 1).toUpperCase() + str.substring(1);
        return temp;
    }

    /**
     * 获得属性
     *
     * @param obj
     * @param att
     * @return
     * @throws Exception
     */
    public Object getter(Object obj, String att) throws Exception {
        Method method = obj.getClass().getMethod("get" + att);
        return method.invoke(obj);
    }


}
