package com.aomei.model;

import java.io.Serializable;
import java.util.Date;

public class Materials implements Serializable {
    private Integer id;

    private String mateName;

    private String mateCode;

    private String mateType;

    private String mateSize;

    private Integer stockNum;

    private Date createTime;

    private Date lastupdateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMateName() {
        return mateName;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName == null ? null : mateName.trim();
    }

    public String getMateCode() {
        return mateCode;
    }

    public void setMateCode(String mateCode) {
        this.mateCode = mateCode == null ? null : mateCode.trim();
    }

    public String getMateType() {
        return mateType;
    }

    public void setMateType(String mateType) {
        this.mateType = mateType == null ? null : mateType.trim();
    }

    public String getMateSize() {
        return mateSize;
    }

    public void setMateSize(String mateSize) {
        this.mateSize = mateSize == null ? null : mateSize.trim();
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Date lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }
}