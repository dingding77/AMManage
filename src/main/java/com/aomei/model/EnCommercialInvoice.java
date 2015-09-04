package com.aomei.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EnCommercialInvoice implements Serializable {
    private Integer id;

    private String toCompany;

    private String invoiceNumber;

    private String fromCompany;

    private String reInfo;

    private Date docTrade;

    private String seaportFrom;

    private String destinationTo;

    private String payment;

    private String issuedBy;

    private Integer createUserid;
    @Getter @Setter
    private List<EnciOrder> enciOrders;
    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToCompany() {
        return toCompany;
    }

    public void setToCompany(String toCompany) {
        this.toCompany = toCompany == null ? null : toCompany.trim();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public String getFromCompany() {
        return fromCompany;
    }

    public void setFromCompany(String fromCompany) {
        this.fromCompany = fromCompany == null ? null : fromCompany.trim();
    }

    public String getReInfo() {
        return reInfo;
    }

    public void setReInfo(String reInfo) {
        this.reInfo = reInfo == null ? null : reInfo.trim();
    }

    public Date getDocTrade() {
        return docTrade;
    }

    public void setDocTrade(Date docTrade) {
        this.docTrade = docTrade;
    }

    public String getSeaportFrom() {
        return seaportFrom;
    }

    public void setSeaportFrom(String seaportFrom) {
        this.seaportFrom = seaportFrom == null ? null : seaportFrom.trim();
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(String destinationTo) {
        this.destinationTo = destinationTo == null ? null : destinationTo.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy == null ? null : issuedBy.trim();
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }
}