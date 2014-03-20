package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Inventory;
import cn.edu.fudan.ss09.pm.service.IInventoryService;

public class InventoryAction extends AbstractBaseAction {
	private List root;
	private Inventory inventory;
	private InputStream inputStream;

	public InputStream getResult() {
		return inputStream;
	}

	public String addInventory() throws UnsupportedEncodingException {
		if (((IInventoryService) service).addInventory(inventory.getISBN(),
				inventory.getEdition(), inventory.getImpression(),
				inventory.getWarehouseId(), inventory.getCurrentCount())
				.equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else
			inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

	public String updateInventory() throws UnsupportedEncodingException {
		// System.out.println(employee.getEmployeeId());
		// System.out.println(employee.getName());
		// System.out.println(employee.getGender());
		// System.out.println(employee.getEmail());

		if (((IInventoryService) service).updateInventory(
				inventory.getInventoryId(), inventory.getISBN(),
				inventory.getEdition(), inventory.getImpression(),
				inventory.getWarehouseId(), inventory.getCurrentCount()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else
			inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

	public String removeInventory() throws UnsupportedEncodingException {
		if (((IInventoryService) service).removeInventory(inventory
				.getInventoryId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else
			inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

	public String getAllInventories() {
		root = ((IInventoryService) service).getInventories();
		if (root != null)
			return SUCCEED;
		return FAIL;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
