package com.aomei.query;

import lombok.Data;

import java.util.Date;

/**
 * 角色查询对象
 * Created by Administrator on 2015/8/30.
 */
@Data
public class RoleQuery extends BaseQuery{
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String roleName;

    private String description;

    private Date createtime;

    private String isDelete="N";

    private String isEnable;

    private String creator;

    private String modify;


}
