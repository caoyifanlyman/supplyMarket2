/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.sql.Date;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Inventory;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 *
 */
@SuppressWarnings("rawtypes")
public class InventoryService extends IInventoryService{

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#addInventory(double, int, int, java.lang.String, int, java.sql.Date)
	 */
	@Override
	public String addInventory(String isbn, int edition, int impression,
			int warehouseId, int currentCount) {
		// TODO Auto-generated method stub
		if(isbn==null||edition<0||impression<0||warehouseId<0||currentCount<0){
			return this.ILLEGAL;
		}
		Date countDate = new Date(System.currentTimeMillis());
		Inventory in = new Inventory(isbn,edition,impression, warehouseId,currentCount,  countDate);
		
		try {
			this.dao.insert(in);
			return this.SUCCEED;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.FAIL;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#removeInventory(java.lang.String)
	 */
	@Override
	public boolean removeInventory(int inventoryId) {
		// TODO Auto-generated method stub
		if(inventoryId<0){
			return this.FALSE;
		}
		try {
			Inventory in = (Inventory) this.dao.findById(Inventory.class, inventoryId);
			this.dao.delete(in);
			return this.TRUE;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#updateInventory(double, int, int, java.lang.String, int, java.sql.Date)
	 */
	@Override
	public boolean updateInventory(int inventoryId,String isbn, int edition, int impression,
			int warehouseId, int currentCount) {
		// TODO Auto-generated method stub
		if(isbn==null||edition<0||impression<0||warehouseId<0||currentCount<0){
			return this.FALSE;
		}
		try {
			Inventory in = (Inventory) this.dao.findById(Inventory.class, inventoryId);
			if(in!=null){
				in.setCurrentCount(currentCount);
				in.setISBN(isbn);
				in.setEdition(edition);
				in.setImpression(impression);
				in.setWarehouseId(warehouseId);
				this.dao.update(in);
			}
			return this.TRUE;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#getInventories()
	 */
	@Override
	public List getInventories() {
		// TODO Auto-generated method stub
		try {
			List inves = this.dao.findAll(Inventory.class);
			return inves;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#findInventories(java.lang.String[])
	 */
	@Override
	public List findInventories(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#getInventoriesByProduct(double, int, int)
	 */
	@Override
	public
	List getInventoriesByProduct(double isbn, int edition, int impression) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from Inventory where isbn = "+isbn+" and edition = "+edition+ "and impression = "+impression);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IInventoryService#getInventoriesByWarehouse(int)
	 */
	@Override
	public
	List getInventoriesByWarehouse(int warehouseId) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from Inventory where warehouseId = "+warehouseId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
