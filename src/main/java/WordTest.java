
import com.aomei.model.EnCommercialInvoice;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

public class WordTest {

    private Configuration configuration = null;

    public WordTest(){
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
    }

    public static void main(String[] args) {
        WordTest test = new WordTest();
        test.createWord();
    }

    public void createWord(){
        String templatePath= ("E:\\Workspaces\\aomei_m\\AMManage\\src\\main\\webapp\\WEB-INF\\template");
        Map<String,Object> dataMap=new HashMap<String,Object>();
        EnCommercialInvoice invoice=new EnCommercialInvoice();
        invoice.setInvoiceNumber("001");
        invoice.setReInfo("11");
        invoice.setDocTrade(new Date());
        invoice.setDestinationTo("bbs");
        dataMap.put("EnCommercialInvoice",invoice);


        Template t=null;
        try {
            configuration.setDirectoryForTemplateLoading(new File(templatePath));  //FTL文件所存在的位置
            t = configuration.getTemplate("CommercialInvoice.xml"); //文件名
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outFile = new File("E:/outFilessa"+Math.random()*10000+".xls");  //生成文件的路径
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            t.process(dataMap, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //这里赋值的时候需要注意,xml中需要的数据你必须提供给它,不然会报找不到某元素错的.
    private void getData(Map<String, Object> dataMap) {
        dataMap.put("title", "标题");
        dataMap.put("year", "2012");
        dataMap.put("month", "2");
        dataMap.put("day", "13");
        dataMap.put("auditor", "鑫");
        dataMap.put("phone", "xxxxxxxxxxxxx");
        dataMap.put("weave", "文涛");
//      dataMap.put("number", 1);
//      dataMap.put("content", "内容"+2);

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("number", i);
            map.put("content", "内容"+i);
            list.add(map);
        }


        dataMap.put("list", list);

    }
}
