package com.aomei.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 生产单实体对象
 */
public class ManufactureOrder {
    private Integer id;

    private String cstmCode;

    private String proNo;

    private Date orderDate;

    private Date deliveryDate;

    private String houdaoRequests;

    private String board;

    private Integer proNum;

    private String proDesc;

    private String styleNo;

    private Date callslipDate;

    private String materialNo;

    private Integer needNum;

    private Integer realNum;

    private Date revertDate;

    private Integer revertNum;

    private String repeatOrder;

    private String newEdition;

    private String proOperator;

    private String proPasteLike;

    private String proTimeConsuming;

    private Date proDate;

    private String fssOperator;

    private Date fssDate;

    private String fssTimeConsuming;

    private String qcOperator;

    private String qcTimeConsuming;

    private String goodsPasteLike;

    private String packDetail;

    private Date qcDate;

    private String proPlanning;

    private String proDocumentary;

    private String remark;
    private String extendInfo;

    @Getter @Setter
    List<ManufactureOrderDetail> detailList;
    /**新增其它查询条件**/

    private String beginOrderDate;

    public String getBeginOrderDate() {
        return beginOrderDate=(beginOrderDate==null?null:beginOrderDate.trim());
    }

    public void setBeginOrderDate(String beginOrderDate) {
        this.beginOrderDate = (beginOrderDate==null?null:beginOrderDate.trim());
    }

    public String getEndOrderDate() {
        return endOrderDate;
    }

    public void setEndOrderDate(String endOrderDate) {
        this.endOrderDate = (endOrderDate==null?null:endOrderDate.trim());
    }

    private String endOrderDate;

    public Integer getId() {
        return id;
    }

    public String getFssTimeConsuming() {
        return fssTimeConsuming;
    }

    public void setFssTimeConsuming(String fssTimeConsuming) {
        this.fssTimeConsuming = fssTimeConsuming == null ? null : fssTimeConsuming.trim();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCstmCode() {
        return cstmCode;
    }

    public void setCstmCode(String cstmCode) {
        this.cstmCode = cstmCode == null ? null : cstmCode.trim();
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo == null ? null : proNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getHoudaoRequests() {
        return houdaoRequests;
    }

    public void setHoudaoRequests(String houdaoRequests) {
        this.houdaoRequests = houdaoRequests == null ? null : houdaoRequests.trim();
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board == null ? null : board.trim();
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc == null ? null : proDesc.trim();
    }

    public String getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo == null ? null : styleNo.trim();
    }

    public Date getCallslipDate() {
        return callslipDate;
    }

    public void setCallslipDate(Date callslipDate) {
        this.callslipDate = callslipDate;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo == null ? null : materialNo.trim();
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public Integer getRealNum() {
        return realNum;
    }

    public void setRealNum(Integer realNum) {
        this.realNum = realNum;
    }

    public Date getRevertDate() {
        return revertDate;
    }

    public void setRevertDate(Date revertDate) {
        this.revertDate = revertDate;
    }

    public Integer getRevertNum() {
        return revertNum;
    }

    public void setRevertNum(Integer revertNum) {
        this.revertNum = revertNum;
    }

    public String getRepeatOrder() {
        return repeatOrder;
    }

    public void setRepeatOrder(String repeatOrder) {
        this.repeatOrder = repeatOrder == null ? null : repeatOrder.trim();
    }

    public String getNewEdition() {
        return newEdition;
    }

    public void setNewEdition(String newEdition) {
        this.newEdition = newEdition == null ? null : newEdition.trim();
    }

    public String getProOperator() {
        return proOperator;
    }

    public void setProOperator(String proOperator) {
        this.proOperator = proOperator == null ? null : proOperator.trim();
    }

    public String getProPasteLike() {
        return proPasteLike;
    }

    public void setProPasteLike(String proPasteLike) {
        this.proPasteLike = proPasteLike == null ? null : proPasteLike.trim();
    }

    public String getProTimeConsuming() {
        return proTimeConsuming;
    }

    public void setProTimeConsuming(String proTimeConsuming) {
        this.proTimeConsuming = proTimeConsuming == null ? null : proTimeConsuming.trim();
    }

    public Date getProDate() {
        return proDate;
    }

    public void setProDate(Date proDate) {
        this.proDate = proDate;
    }

    public String getFssOperator() {
        return fssOperator;
    }

    public void setFssOperator(String fssOperator) {
        this.fssOperator = fssOperator == null ? null : fssOperator.trim();
    }

    public Date getFssDate() {
        return fssDate;
    }

    public void setFssDate(Date fssDate) {
        this.fssDate = fssDate;
    }

    public String getQcOperator() {
        return qcOperator;
    }

    public void setQcOperator(String qcOperator) {
        this.qcOperator = qcOperator == null ? null : qcOperator.trim();
    }

    public String getQcTimeConsuming() {
        return qcTimeConsuming;
    }

    public void setQcTimeConsuming(String qcTimeConsuming) {
        this.qcTimeConsuming = qcTimeConsuming == null ? null : qcTimeConsuming.trim();
    }

    public String getGoodsPasteLike() {
        return goodsPasteLike;
    }

    public void setGoodsPasteLike(String goodsPasteLike) {
        this.goodsPasteLike = goodsPasteLike == null ? null : goodsPasteLike.trim();
    }

    public String getPackDetail() {
        return packDetail;
    }

    public void setPackDetail(String packDetail) {
        this.packDetail = packDetail == null ? null : packDetail.trim();
    }

    public Date getQcDate() {
        return qcDate;
    }

    public void setQcDate(Date qcDate) {
        this.qcDate = qcDate;
    }

    public String getProPlanning() {
        return proPlanning;
    }

    public void setProPlanning(String proPlanning) {
        this.proPlanning = proPlanning == null ? null : proPlanning.trim();
    }

    public String getProDocumentary() {
        return proDocumentary;
    }

    public void setProDocumentary(String proDocumentary) {
        this.proDocumentary = proDocumentary == null ? null : proDocumentary.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}