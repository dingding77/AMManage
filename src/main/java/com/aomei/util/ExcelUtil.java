package com.aomei.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

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
}
