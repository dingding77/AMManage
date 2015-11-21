package com.aomei.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class DeliveryGoods implements Serializable {
    private Integer id;

    private String goodsNo;

    private String contractNo;

    private String goodsName;

    private Integer goodsNum;
    @Getter @Setter
    private String goodsUnit;
    @Getter @Setter
    private Double goodsPrice;
    @Getter @Setter
    private Double goodsAmount;

    private Integer realsendNum;

    private Integer denoteId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo == null ? null : goodsNo.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getRealsendNum() {
        return realsendNum;
    }

    public void setRealsendNum(Integer realsendNum) {
        this.realsendNum = realsendNum;
    }

    public Integer getDenoteId() {
        return denoteId;
    }

    public void setDenoteId(Integer denoteId) {
        this.denoteId = denoteId;
    }
}