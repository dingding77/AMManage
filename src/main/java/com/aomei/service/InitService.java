package com.aomei.service;

import com.aomei.dao.ManufactureOrderDao;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * 初始化服务类
 * Created by Administrator on 2015/8/16.
 */
public class InitService {

    @Getter
    @Setter
    private ManufactureOrderDao manufactureOrderDao;

    /**
     * 生产单号
     */
    public static Map<String,String> orderNoMap=new Hashtable<String, String>();

    public static final String ORDER_NO_KEY="ORDER_NO";

    /**
     * 采购单号
     */
    public static Map<String,String> purchaseNoMap=new Hashtable<String, String>();

    public static final String PURCHASE_NO_KEY="PURCHASE_NO";

    public void init(){
        try{
            String maxOrderNo= manufactureOrderDao.findMaxOrderNo();
            if(StringUtils.isEmpty(maxOrderNo)){
                maxOrderNo="AP000001";
            }
            orderNoMap.put(ORDER_NO_KEY,maxOrderNo);
        }catch (Exception e){

        }
    }

    public synchronized void nextOrderNo(){
        String maxOrderNo=orderNoMap.get(ORDER_NO_KEY);

        if(maxOrderNo==null){
            maxOrderNo="AP000001";
        }else{
            maxOrderNo=maxOrderNo.replace("AP","");
            int orderNoNums=Integer.parseInt(maxOrderNo);
            maxOrderNo="AP"+InitService.appendZero(orderNoNums,6);
        }
        orderNoMap.put(ORDER_NO_KEY,maxOrderNo);
    }
    public static String appendZero(int num, int length) {
        //String.valueOf()是用来将其他类型的数据转换为string型数据的
        String tmpString = String.valueOf(num);
        for (int i = tmpString.length(); i < length; i++) {
            tmpString = "0" + tmpString;
        }
        return tmpString;
    }
}
