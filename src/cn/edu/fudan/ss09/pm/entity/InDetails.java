/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: InDetails                                             */
/*==============================================================*/
/*create table InDetails
(
   inDetailsId          char(8) not null,
   ISBN                 numeric(13,0),
   edition              int,
   impression           int,
   inOrderId            char(8) not null,
   warehouseId          char(8),
   inCount              int not null,
   inItemPrice          float(8,2) not null,
   primary key (inDetailsId)
);
*/
public class InDetails {
	private int inDetailsId;
	private String ISBN;
	//private 
	private int edition;
	private int impression;
	private int inOrderId;
	private int warehouseId;
	private int inCount;
	private float inItemPrice;

	public InDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InDetails(String iSBN, int edition, int impression,
			int inOrderId, int warehouseId, int inCount, float inItemPrice) {
		super();
		setISBN(iSBN);
		this.edition = edition;
		this.impression = impression;
		this.inOrderId = inOrderId;
		this.warehouseId = warehouseId;
		this.inCount = inCount;
		this.inItemPrice = inItemPrice;
	}
	public int getInDetailsId() {
		return inDetailsId;
	}
	public void setInDetailsId(int inDetailsId) {
		this.inDetailsId = inDetailsId;
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

	public int getInOrderId() {
		return inOrderId;
	}
	public void setInOrderId(int inOrderId) {
		this.inOrderId = inOrderId;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	public int getInCount() {
		return inCount;
	}
	public void setInCount(int inCount) {
		this.inCount = inCount;
	}
	public float getInItemPrice() {
		return inItemPrice;
	}
	public void setInItemPrice(float inItemPrice) {
		this.inItemPrice = inItemPrice;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
}
