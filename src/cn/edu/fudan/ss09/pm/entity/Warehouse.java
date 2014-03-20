/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: Warehouse                                             */
/*==============================================================*/
/*create table Warehouse
(
   warehouseId          char(8) not null,
   warehouseName        varchar(20) not null,
   warehouseLocation    varchar(100) not null,
   warehouseStatus      int not null,
   primary key (warehouseId)
);
*/
public class Warehouse {
	private int warehouseId;
	private String warehouseName;
	private String warehouseLocation;
	private int warehouseStatus;

	public Warehouse(String warehouseName, String warehouseLocation,
			int warehouseStatus) {
		super();
		this.warehouseName = warehouseName;
		this.warehouseLocation = warehouseLocation;
		this.warehouseStatus = warehouseStatus;
	}
	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}
	public String getWarehouseLocation() {
		return warehouseLocation;
	}
	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}
	public int getWarehouseStatus() {
		return warehouseStatus;
	}
	public void setWarehouseStatus(int warehouseStatus) {
		this.warehouseStatus = warehouseStatus;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	
}
