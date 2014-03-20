package cn.edu.fudan.ss09.pm.action;

import java.io.InputStream;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Warehouse;
import cn.edu.fudan.ss09.pm.service.IWarehouseService;

public class WarehouseAction extends AbstractBaseAction{
	private List root;
	private Warehouse warehouse;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String getAllWarehouse(){
		root = ((IWarehouseService)service).getAllWarehouse();
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}
