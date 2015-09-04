package com.aomei.model;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String type;

    private String zhSize;

    private String totalSize;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getZhSize() {
        return zhSize;
    }

    public void setZhSize(String zhSize) {
        this.zhSize = zhSize == null ? null : zhSize.trim();
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize == null ? null : totalSize.trim();
    }
}