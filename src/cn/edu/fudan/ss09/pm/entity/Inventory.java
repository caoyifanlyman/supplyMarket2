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
/* Table: Inventory                                             */
/*==============================================================*/
/*create table Inventory
(
   inventoryId          char(8) not null,
   ISBN                 numeric(13,0),
   edition              int,
   impression           int,
   warehouseId          char(8),
   currentCount         int not null,
   countDate            date not null,
   primary key (inventoryId)
);
*/
public class Inventory {
	private int inventoryId;
	private String ISBN;
	private int edition;
	private int impression;
	private int warehouseId;
	private int currentCount;
	private Date countDate;

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Inventory(String iSBN, int edition, int impression, int warehouseId,
			int currentCount, Date countDate) {
		super();
		setISBN(iSBN);
		this.edition = edition;
		this.impression = impression;
		this.warehouseId = warehouseId;
		this.currentCount = currentCount;
		this.countDate = countDate;
	}
	public int getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public int getWarehouseId() {
		return warehouseId;
	}

	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	public int getImpression() {
		return impression;
	}
	public void setImpression(int impression) {
		this.impression = impression;
	}

	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public Date getCountDate() {
		return countDate;
	}
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	
}
