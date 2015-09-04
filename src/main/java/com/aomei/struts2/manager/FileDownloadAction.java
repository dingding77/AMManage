package com.aomei.struts2.manager;

import com.aomei.util.FreeMakerStreamUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2015/7/16.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager")
@Component
public class FileDownloadAction extends ActionSupport{
    //下载文件时的文件名

    @Action(value = "exportWord",
            results = { @Result(name = "excel", type = "stream")
            })
    public void  excel(){
        //模板参数
        Map<String,Object> templateData = new HashMap<String, Object>();
        templateData.put("ALIST", "");

        String filedisplay="111.xls";
        try {
            String templatePath=FreeMakerStreamUtil.templatePath("/template");
            FreeMakerStreamUtil fsu=new FreeMakerStreamUtil(templatePath,"SHD.xml",filedisplay,templateData, ServletActionContext.getResponse());
            fsu.createExce();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{

        }
    }


@Action(value = "excelByBoiler",
        results = { @Result(name = "excel", type = "stream")
        })
    public void excelByBoiler() {

    //获取request对象
    HttpServletRequest request = ServletActionContext.getRequest();
    //获取response 对象
    HttpServletResponse response = ServletActionContext.getResponse();

    String begin="2015-01-02";
        String end="2015-08-01";
        if(request.getParameter("begin")!=null && request.getParameter("end") !=null){
            begin=request.getParameter("begin");
            end = request.getParameter("end");
        }
    //getServletContext().getRealPath("")
        try {

            ActionContext ac = ActionContext.getContext();
            ServletContext sc = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
            String temp = ServletActionContext.getRequest().getRealPath("/");

            String path =temp+ "/excelTemplate/TJ.xls";   //这个是我的excel模板
            InputStream in = new FileInputStream(new File(path));
            SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //为了转时间
            HSSFWorkbook work = new HSSFWorkbook(in);
            // 得到excel的第0张表
            HSSFSheet sheet = work.getSheetAt(0);
            // 得到第1行的第一个单元格的样式
            HSSFRow rowCellStyle = sheet.getRow(2);

            HSSFCellStyle columnOne = rowCellStyle.getCell(0).getCellStyle();
            HSSFCellStyle columnOne1 = rowCellStyle.getCell(1).getCellStyle();
            HSSFCellStyle columnOne2 = rowCellStyle.getCell(4).getCellStyle();

            // 这里面的行和列的数法与计算机里的一样，从0开始是第一
            // 填充title数据

            HSSFRow row = sheet.getRow(0);
            HSSFCell cell = row.getCell(2);
            cell.setCellValue("一号机组启停统计(" + begin + "～" + end + ")");
            int i = 2;// 计数器
            //集合对象
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> mapobj=new HashMap<String, Object>();



            for(int n=0;n<10;n++){
                mapobj=new HashMap<String, Object>();
                mapobj.put("actiondatetime",new Date());
                mapobj.put("actionstatus","220");
                mapobj.put("logmemo","cc");
                mapobj.put("rundays",22);
                mapobj.put("stophours",2);
                list.add(mapobj);
            }

// 创建每个单元格，添加样式，最后合并
            work.getSheetAt(0).shiftRows(3, 3, list.size() - 1); // 把第三行移动到列表的长度-1的地方  这个地方我是要把模板的第四行放到最后一行
            int numberbegin = 0;// 统计
            int numberend = 0;// 统计
            for (Map map : list) {
                row = sheet.createRow(i);// 得到行
                cell = row.createCell(0);// 得到第0个单元格
                if (!(map.get("actiondatetime") == null)) {
                    cell.setCellValue(simpleFormat.format(map.get("actiondatetime")));
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnOne);// 填充样式


                cell = row.createCell(1);
                if (!(map.get("actionstatus") == null)) {
                    cell.setCellValue(map.get("actionstatus").toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnOne1);// 填充样式
                cell = row.createCell(2);
                if (!(map.get("logmemo") == null)) {
                    cell.setCellValue(map.get("logmemo").toString());
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnOne1);// 填充样式
                cell = row.createCell(3);
                if (!(map.get("rundays") == null)) {
                    cell.setCellValue(map.get("rundays").toString());
                    numberbegin++;
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnOne1);// 填充样式
                cell = row.createCell(4);
                if (!(map.get("stophours") == null)) {
                    cell.setCellValue(map.get("stophours").toString());
                    numberend++;
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(columnOne2);// 填充样式
                // .....给每个单元格填充数据和样式
                i++;
            }
            HSSFRow row1 = sheet.getRow(list.size() + 2);
            HSSFCell cell1 = row1.getCell(0);
            cell1.setCellValue("启停共" + (numberbegin+numberend) + "次，" +  "其中启" + numberbegin
                    + "次，停" + numberend + "次");//
/*******************************sheet二*******************************************/
            HSSFSheet sheet1 = work.getSheetAt(1);
// 得到第1行的第一个单元格的样式
            HSSFRow rowCellStyle1 = sheet1.getRow(2);
            HSSFCellStyle columnOne01 = rowCellStyle1.getCell(0).getCellStyle();
            HSSFCellStyle columnOne02 = rowCellStyle1.getCell(1).getCellStyle();
            HSSFCellStyle columnOne03 = rowCellStyle1.getCell(4).getCellStyle();
            HSSFRow row2 = sheet1.getRow(0);
            HSSFCell cell2 = row2.getCell(2);
            cell2.setCellValue("二号机组启停统计(" + begin + "～" + end + ")");
            int j = 2;// 计数器
            List<Map<String, Object>> list2 = list;
            // 创建每个单元格，添加样式，最后合并
            work.getSheetAt(1).shiftRows(3, 3, list2.size() - 1); // 把第三行移动到列表的长度-1的地方
            int numberbegin1 = 0;// 统计
            int numberend1 = 0;// 统计
            for (Map map : list2) {
                row2 = sheet1.createRow(j);// 得到行
                cell2 = row2.createCell(0);// 得到第0个单元格
                if (!(map.get("actiondatetime") == null)) {
                    cell2.setCellValue(simpleFormat.format(map.get("actiondatetime")));
                } else {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(columnOne01);// 填充样式
                cell2 = row2.createCell(1);
                if (!(map.get("actionstatus") == null)) {
                    cell2.setCellValue(map.get("actionstatus").toString());
                } else {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(columnOne02);// 填充样式
                cell2 = row2.createCell(2);
                if (!(map.get("logmemo") == null)) {
                    cell2.setCellValue(map.get("logmemo").toString());
                } else {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(columnOne02);// 填充样式
                cell2 = row2.createCell(3);
                if (!(map.get("rundays") == null)) {
                    cell2.setCellValue(map.get("rundays").toString());
                    numberbegin1++;
                } else {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(columnOne02);// 填充样式
                cell2 = row2.createCell(4);
                if (!(map.get("stophours") == null)) {
                    cell2.setCellValue(map.get("stophours").toString());
                    numberend1++;
                } else {
                    cell2.setCellValue("");
                }
                cell2.setCellStyle(columnOne03);// 填充样式
                // .....给每个单元格填充数据和样式
                i++;
            }
            HSSFRow row3 = sheet1.getRow(list2.size() + 2);
            HSSFCell cell3 = row3.getCell(0);
            cell3.setCellValue("启停共" + (numberbegin1+numberend1) + "次，" +  "其中启" + numberbegin1
                    + "次，停" + numberend1 + "次");//


/****************************输出流*****************************************/
            String address = simpleFormat.format(new Date());
            OutputStream os = response.getOutputStream();// 取得输出流
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition",
                    "attachment;filename=jzqttj_"+address+".xls");
            work.write(os);
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入流错误");
            e.printStackTrace();
        }

    }
}
