package com.aomei.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Administrator on 2015/6/28.
 */
@Slf4j
public class JacobUtil {

    // 8 代表word保存成html
    public static final int WORD_HTML = 8;
    public static final int EXCEL_HTML = 44;

    public static void main(String[] args) {
        String docfile = "E:\\SHANGHAI_AOMEI\\File\\送货单(1).xls";
        String htmlfile = "E:\\SHANGHAI_AOMEI\\File\\送货单(1).html";
        JacobUtil.excelToHtml(docfile, htmlfile);
    }

    public static void excelToHtml(String xlsfile,String htmlfile){
        //启动Excel
        ActiveXComponent app=new ActiveXComponent("Excel.Application");
        try{
            app.setProperty("Visible",new Variant(false));
            Dispatch excels=app.getProperty("Workbooks").toDispatch();
            Dispatch excel=Dispatch.invoke(
                    excels,
                    "Open",
                    Dispatch.Method,
                    new Object[]{xlsfile,new Variant(false),new Variant(true)},new int[1]).toDispatch();
            Dispatch.invoke(excel,"SaveAs",Dispatch.Method,new Object[]{htmlfile,new Variant(EXCEL_HTML)},new int[1]);
            Variant f=new Variant(false);
           Dispatch.call(excel,"Close",f);
        }catch (Exception e){
            log.error("excelToHtml异常={}",e);
        }finally {
            app.invoke("Quit",new Variant[]{});
        }
    }
}
