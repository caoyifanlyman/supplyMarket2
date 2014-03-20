/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: OutDetails                                            */
/*==============================================================*/
/*create table OutDetails
(
   outDetailsId         char(8) not null,
   warehouseId          char(8),
   ISBN                 numeric(13,0),
   edition              int,
   impression           int,
   outOrderId           char(8) not null,
   outCount             int not null,
   outItemPrice         float(8,2) not null,
   primary key (outDetailsId)
);
*/

public class OutDetails {
	private int outDetailsId;
	private int warehouseId;
	private String ISBN;
	private int edition;
	private int impression;
	private int outOrderId;
	private int outCount;
	private float outItemPrice;
	
	public OutDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OutDetails(String iSBN, int edition, int impression,
			int outOrderId, int warehouseId, int outCount, float outItemPrice) {
		super();
		setISBN(iSBN);
		this.edition = edition;
		this.impression = impression;
		this.outOrderId = outOrderId;
		this.warehouseId = warehouseId;
		this.outCount = outCount;
		this.outItemPrice = outItemPrice;
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

	public int getOutCount() {
		return outCount;
	}
	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}
	public float getOutItemPrice() {
		return outItemPrice;
	}
	public void setOutItemPrice(float outItemPrice) {
		this.outItemPrice = outItemPrice;
	}
	public int getOutDetailsId() {
		return outDetailsId;
	}
	public void setOutDetailsId(int outDetailsId) {
		this.outDetailsId = outDetailsId;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public int getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(int outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
