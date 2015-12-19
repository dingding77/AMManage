package com.aomei.model;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class EnciOrder implements Serializable {
    private Integer id;

    private String orderNo;

    private String goodsDesc;

    private Long price;

    private String psc;

    private String totalAmount;

    private Integer enciId;
    private String priceUnit;
    private String priceShow;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public String getPriceShow() {
        if(StringUtils.isNotEmpty(priceUnit)&&price!=null){
            return price+priceUnit;
        }
        return priceShow;
    }

    public void setPriceShow(String priceShow) {
        this.priceShow = priceShow;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc == null ? null : goodsDesc.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        if(StringUtils.isNotEmpty(priceUnit)&&price!=null){
            this.priceShow= price+priceUnit;
        }
        this.price = price;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc == null ? null : psc.trim();
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount == null ? null : totalAmount.trim();
    }

    public Integer getEnciId() {
        return enciId;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        if(StringUtils.isNotEmpty(priceUnit)&&price!=null){
            this.priceShow= price+priceUnit;
        }
        this.priceUnit = priceUnit;
    }

    public void setEnciId(Integer enciId) {
        this.enciId = enciId;
    }
}