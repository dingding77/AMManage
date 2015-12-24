package com.aomei.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DeliveryNote implements Serializable {
    private Integer id;

    private String customerName;

    private String deliverNo;

    private String deliverWay;

    private Date deliverDate;

    private String paymentWay;

    private String doctradeUser;

    private Date doctradeDate;

    private String busiUser;

    private Integer createUserid;
    @Getter @Setter
    private String relationOrderType;
    @Getter @Setter
    private String orderNo;
    @Getter @Setter
    private String receiver;
    @Getter @Setter
    private Date createTime;
    @Getter @Setter
    private String isOk;
    @Getter @Setter
    private String isDelete;
    @Getter @Setter
    private List<DeliveryGoods> deliveryGoodsList;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getDeliverNo() {
        return deliverNo;
    }

    public void setDeliverNo(String deliverNo) {
        this.deliverNo = deliverNo;
    }

    public String getDeliverWay() {
        return deliverWay;
    }

    public void setDeliverWay(String deliverWay) {
        this.deliverWay = deliverWay == null ? null : deliverWay.trim();
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay == null ? null : paymentWay.trim();
    }

    public String getDoctradeUser() {
        return doctradeUser;
    }

    public void setDoctradeUser(String doctradeUser) {
        this.doctradeUser = doctradeUser == null ? null : doctradeUser.trim();
    }

    public Date getDoctradeDate() {
        return doctradeDate;
    }

    public void setDoctradeDate(Date doctradeDate) {
        this.doctradeDate = doctradeDate;
    }

    public String getBusiUser() {
        return busiUser;
    }

    public void setBusiUser(String busiUser) {
        this.busiUser = busiUser == null ? null : busiUser.trim();
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }
}