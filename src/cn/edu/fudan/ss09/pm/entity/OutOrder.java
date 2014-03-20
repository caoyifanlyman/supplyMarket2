/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

import java.sql.Date;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: OutOrder                                              */
/*==============================================================*/
/*create table OutOrder
(
   outOrderId           char(8) not null,
   customerId           char(8) not null,
   outOrderDate         date not null,
   outTotalPrice        float(8,2) not null,
   outDiscountRate      numeric(3,2) not null,
   outDiscountedPrice   float(8,2) not null,
   primary key (outOrderId)
);*/
public class OutOrder {
	private int outOrderId;
	private int customerId;
	private Date outOrderDate;
	private float outTotalPrice;
	private float outDiscountRate;
	private float outDiscountedPrice;
	
	public OutOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OutOrder(int customerId, Date outOrderDate, float outTotalPrice,
			float outDiscountRate, float outDiscountedPrice) {
		super();
		this.customerId = customerId;
		this.outOrderDate = outOrderDate;
		this.outTotalPrice = outTotalPrice;
		this.outDiscountRate = outDiscountRate;
		this.outDiscountedPrice = outDiscountedPrice;
	}

	public int getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(int outOrderId) {
		this.outOrderId = outOrderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getOutOrderDate() {
		return outOrderDate;
	}
	public void setOutOrderDate(Date outOrderDate) {
		this.outOrderDate = outOrderDate;
	}
	public float getOutTotalPrice() {
		return outTotalPrice;
	}
	public void setOutTotalPrice(float outTotalPrice) {
		this.outTotalPrice = outTotalPrice;
	}
	public float getOutDiscountRate() {
		return outDiscountRate;
	}
	public void setOutDiscountRate(float outDiscountRate) {
		this.outDiscountRate = outDiscountRate;
	}
	public float getOutDiscountedPrice() {
		return outDiscountedPrice;
	}
	public void setOutDiscountedPrice(float outDiscountedPrice) {
		this.outDiscountedPrice = outDiscountedPrice;
	}
	
}
