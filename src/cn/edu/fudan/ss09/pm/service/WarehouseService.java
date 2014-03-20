/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Warehouse;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 *
 */
public class WarehouseService extends IWarehouseService {

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IWarehouseService#getWarehouse(java.lang.String)
	 */
	@Override
	public Warehouse getWarehouse(int warehouseId) {
		// TODO Auto-generated method stub
		try {
			Warehouse house = (Warehouse) this.dao.findById(Warehouse.class, warehouseId);
			return house;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IWarehouseService#getAllWarehouse()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllWarehouse() {
		// TODO Auto-generated method stub
		try {
			return this.dao.findAll(Warehouse.class);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
