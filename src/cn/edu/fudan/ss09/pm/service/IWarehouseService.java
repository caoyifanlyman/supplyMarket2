package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Warehouse;


public abstract class IWarehouseService  extends IService{
	//the inventory page might call this to check warehouse info
	public abstract Warehouse getWarehouse(int warehouseId);
	//the inventory page might call this to check warehouse info
	@SuppressWarnings("rawtypes")
	public abstract List getAllWarehouse();
}
