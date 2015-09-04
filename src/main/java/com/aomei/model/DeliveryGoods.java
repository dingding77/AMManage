package com.aomei.model;

import java.io.Serializable;

public class DeliveryGoods implements Serializable {
    private Integer id;

    private String goodsNo;

    private String contractNo;

    private String goodsName;

    private Integer goodsNum;

    private String goodsUnit;

    private Long goodsPrice;

    private Long goodsAmount;

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

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit == null ? null : goodsUnit.trim();
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Long getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Long goodsAmount) {
        this.goodsAmount = goodsAmount;
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