package com.aomei.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer level;

    private Integer parentid;

    private String resources;

    private String iconImg;

    private Date createtime;

    private String menuType;

    private String methodName;

    private String isDelete="N";

    private List<Menu> childrens;



}