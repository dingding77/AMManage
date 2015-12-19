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
    private String payment;
    private Date deliveryTime;
    private String creator;
    private String touching;//制单
    private String auditor;
    private String director;
    private String department;
    private String salesman;
    /**采购单号 格式AMPO000001**/
    private String purchaseNo;
    private String extInfo;//拓展信息
    private Date createTime;
    private String companyDate;
    private String supplierDate;
    private String companyRemark;
    private String receiver;
    public List<PurchaseDetail> detailList;
    private static final long serialVersionUID = 1L;


}