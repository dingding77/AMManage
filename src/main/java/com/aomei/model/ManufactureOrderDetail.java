package com.aomei.model;

import java.io.Serializable;
import java.util.Date;

public class ManufactureOrderDetail implements Serializable {
    private Integer id;

    private String name;

    private String num;

    private String size;

    private String kh;

    private String colorSize;

    private String hwbh;

    private Integer orderId;

    private String kebh;

    private String isDelete;

    private Date createTime;

    private Date modifyTime;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num == null ? null : num.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh == null ? null : kh.trim();
    }

    public String getColorSize() {
        return colorSize;
    }

    public void setColorSize(String colorSize) {
        this.colorSize = colorSize == null ? null : colorSize.trim();
    }

    public String getHwbh() {
        return hwbh;
    }

    public void setHwbh(String hwbh) {
        this.hwbh = hwbh == null ? null : hwbh.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getKebh() {
        return kebh;
    }

    public void setKebh(String kebh) {
        this.kebh = kebh == null ? null : kebh.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
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
}