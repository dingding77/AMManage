package com.aomei.model;

import java.io.Serializable;
import java.util.Date;

public class ProductInfo implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String type;

    private String pantoneNo;

    private String size;

    private String pstp;

    private Date createTime;

    private Date modifyTime;

    private String isDelete;

    private String price;

    private String styleNo;

    private static final long serialVersionUID = 1L;

    public String getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

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

    public String getPantoneNo() {
        return pantoneNo;
    }

    public void setPantoneNo(String pantoneNo) {
        this.pantoneNo = pantoneNo == null ? null : pantoneNo.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getPstp() {
        return pstp;
    }

    public void setPstp(String pstp) {
        this.pstp = pstp == null ? null : pstp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}