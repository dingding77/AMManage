package com.aomei.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2015/8/29.
 */
@Data
public class MenuQuery extends BaseQuery{
    private Integer id;

    private String name;

    private Integer level;

    private Integer parentid;

    private String resources;

    private String iconImg;

    private Date createtime;

    private String menuType;

    private String isDelete="N";

    @Override
    public MenuQuery clone() {
        try {
            return (MenuQuery)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }finally {
            return null;
        }
    }
}
