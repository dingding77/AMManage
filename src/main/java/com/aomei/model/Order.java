package com.aomei.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
    private int orderId;
    private String orderName;
    private String orderDate;
    private String orderType;
    private String orderContent;
    private double orderAmt;
}
