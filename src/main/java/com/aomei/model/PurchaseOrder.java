package com.aomei.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PurchaseOrder implements Serializable {
    private Integer id;

    private String buyerId;

    private String supplierName;

    private String supplierContract;

    private String supplierPhone;

    private Date purchaseDate;

    private String isDelete;

    private String creator;

    private String extInfo;//拓展信息
    private Date createTime;
    public List<PurchaseDetail> detailList;
    private static final long serialVersionUID = 1L;


}