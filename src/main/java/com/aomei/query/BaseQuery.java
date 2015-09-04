package com.aomei.query;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2015/8/29.
 */
public class BaseQuery {
    @Setter
    private int limit=20;
    @Setter
    private int start=0;
    @Getter @Setter
    public int page;               //分页参数(页数)
    @Getter @Setter
    public int rows;               //分页参数(行数)
    /**排序字段**/
    @Getter @Setter
    private String orderFiled;
    /**排序方式**/
    @Getter @Setter
    private String orderType;
    @Getter @Setter
    /**标识是否为Count()查询 Y:否 N:是**/
    private String isCount="N";

    public int getLimit(){
        if(rows>0){
            limit=rows;
        }
        return limit;
    }

    public int getStart(){
        if(page>0){
            start=(page-1)*rows;
        }
        return start;
    }
}
