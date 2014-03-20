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
/* Table: InOrder                                               */
/*==============================================================*/
/*create table InOrder
(
   inOrderId            char(8) not null,
   customerId           char(8) not null,
   inOrderDate          date not null,
   inTotalPrice         float(8,2) not null,
   inDiscountRate       numeric(3,2) not null,
   inDiscountedPrice    float(8,2) not null,
   primary key (inOrderId)
);
*/

public class InOrder {
	private int inOrderId;
	private int customerId;
	private Date inOrderDate;
	private float inTotalPrice;
	private float inDiscountRate;
	private float inDiscountedPrice;

	public InOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InOrder(int customerId, Date inOrderDate, float inTotalPrice,
			float inDiscountRate, float inDiscountedPrice) {
		super();
		this.customerId = customerId;
		this.inOrderDate = inOrderDate;
		this.inTotalPrice = inTotalPrice;
		this.inDiscountRate = inDiscountRate;
		this.inDiscountedPrice = inDiscountedPrice;
	}

	public int getInOrderId() {
		return inOrderId;
	}
	public void setInOrderId(int inOrderId) {
		this.inOrderId = inOrderId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Date getInOrderDate() {
		return inOrderDate;
	}
	public void setInOrderDate(Date inOrderDate) {
		this.inOrderDate = inOrderDate;
	}
	public float getInTotalPrice() {
		return inTotalPrice;
	}
	public void setInTotalPrice(float inTotalPrice) {
		this.inTotalPrice = inTotalPrice;
	}
	public float getInDiscountRate() {
		return inDiscountRate;
	}
	public void setInDiscountRate(float inDiscountRate) {
		this.inDiscountRate = inDiscountRate;
	}
	public float getInDiscountedPrice() {
		return inDiscountedPrice;
	}
	public void setInDiscountedPrice(float inDiscountedPrice) {
		this.inDiscountedPrice = inDiscountedPrice;
	}
	
}
