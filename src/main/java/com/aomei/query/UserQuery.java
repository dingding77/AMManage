package com.aomei.query;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2015/8/29.
 */
@Data
public class UserQuery extends BaseQuery{

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String username;

    private String pwd;

    private String loginIp;

    private String enable;

    private String phone;

    private String email;

    private String remark;

    private Date createtime;

    private String isDelete="N";


}
