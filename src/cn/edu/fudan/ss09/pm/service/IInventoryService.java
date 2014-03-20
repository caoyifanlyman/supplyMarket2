package cn.edu.fudan.ss09.pm.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class IInventoryService extends IService{
	//return inventory id if success or null if failure
	public abstract String addInventory(String isbn,int edition,int impression,int warehouseId,int currentCount);
	//return true if success
	public abstract boolean removeInventory(int inventoryId);
	//return true if success
	public abstract boolean updateInventory(int inventoryId,String isbn,int edition,int impression,int warehouseId,int currentCountt);
	//return the list of all inventories
	public abstract List getInventories();
	//return the list of inventories with special product
	public abstract List getInventoriesByProduct(double isbn,int edition,int impression);
	//return the list of inventories with special warehouse
	public abstract List getInventoriesByWarehouse(int warehouseId);
	//optional: search for certain inventory, might be done in js
	//the args are going to be defined
	public abstract List findInventories(String args[]);
}
