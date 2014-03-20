/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.fudan.ss09.pm.entity.Inventory;
import cn.edu.fudan.ss09.pm.entity.OutDetails;
import cn.edu.fudan.ss09.pm.entity.OutOrder;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 * 
 */
@SuppressWarnings("rawtypes")
public class OutOrderService extends IOutOrderService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#addOrder(int,
	 * java.util.List, double)
	 */
	@Override
	public String addOrder(int customerId, List<OutDetails> outDetails,
			float discountedRate) {
		// TODO Auto-generated method stub
		Map<OutDetails, Inventory> inves = new HashMap<OutDetails, Inventory>();
		Inventory in = null;
		float totalPrice = 0;
		for (OutDetails d : outDetails) {
			if ((in = this.checkIsEnough(d)) == null) {
				return this.FAIL;
			} else {
				totalPrice += d.getOutItemPrice() * d.getOutCount();
				in.setCurrentCount(in.getCurrentCount() - d.getOutCount());
				inves.put(d, in);
			}
		}

		/*
		 * OutOrder(int customerId, Date outOrderDate, float outTotalPrice,
		 * float outDiscountRate, float outDiscountedPrice)
		 */
		float discountPrice = totalPrice * discountedRate;
		Date d = new Date(System.currentTimeMillis());
		OutOrder order = new OutOrder(customerId, d, totalPrice,
				discountedRate, discountPrice);

		try {
			this.dao.insert(order);
			for (OutDetails out : outDetails) {
				out.setOutOrderId(order.getOutOrderId());
				this.dao.insert(out);
				this.dao.update(inves.get(out));
			}
			return this.SUCCEED;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this.FAIL;
	}

	private Inventory checkIsEnough(OutDetails detail) {
		Inventory in = this.getInventoryByDetail(detail);
		if (in != null && in.getCurrentCount() > detail.getOutCount()) {
			return in;
		}
		return null;
	}

	private Inventory getInventoryByDetail(OutDetails detail) {

		try {
			return (Inventory) ((this.dao.find("from Inventory where ISBN = '"
					+ detail.getISBN() + "' and edition = "
					+ detail.getEdition() + " and impression="
					+ detail.getImpression() + " and warehouseId = "
					+ detail.getWarehouseId())).get(0));
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private OutOrder getOutOrderById(int outOrderId) {
		try {
			return (OutOrder) this.dao.findById(OutOrder.class, outOrderId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private boolean checkOrderHasDetail(int orderId) {
		try {
			OutOrder order = (OutOrder) this.dao.findById(OutOrder.class,
					orderId);
			if (order != null) {
				List dl = this.dao.find("from OutDetails where outOrderId = "
						+ orderId);
				if (dl != null && dl.size() > 0) {
					return true;
				}
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#removeOrder(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean removeOrder(int orderId) {
		// TODO Auto-generated method stub
		OutOrder order = this.getOutOrderById(orderId);
		try {
			if (order != null) {
				List<OutDetails> details = this.findDetails(orderId);
				for (OutDetails d : details) {
					this.removeDetail(d.getOutDetailsId());
				}
				this.dao.delete(order);
				return true;
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#updateOrder(int, int)
	 */
	@Override
	public boolean updateOrder(int orderId, int customerId) {
		// TODO Auto-generated method stub
		try {
			OutOrder order = (OutOrder) this.dao.findById(OutOrder.class,
					orderId);
			order.setCustomerId(customerId);
			this.dao.update(order);
			return this.TRUE;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#getOrders()
	 */

	@Override
	public List getOrders() {
		// TODO Auto-generated method stub
		try {
			return this.dao.findAll(OutOrder.class);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.edu.fudan.ss09.pm.service.IOutOrderService#findOrders(java.lang.String
	 * [])
	 */
	@Override
	public List getOrdersByCustomerId(int customerId) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from OutOrder where customerId = "
					+ customerId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#addDetail(double, int,
	 * int, int, int, int, double)
	 */
	@Override
	public String addDetail(String isbn, int edition, int impression,
			int orderId, int warehouseId, int count, float itemPrice) {
		// TODO Auto-generated method stub

		/*
		 * OutDetails(int warehouseId, String iSBN, int edition, int impression,
		 * int outOrderId, int outCount, float outItemPrice) OutDetails(String
		 * iSBN, int edition, int impression, int outOrderId, int warehouseId,
		 * int outCount, float outItemPrice)
		 */

		System.out.println("OutDetails");
		OutDetails detail = new OutDetails(isbn, edition, impression, -1,
				warehouseId, count, itemPrice);
		Inventory in = this.checkIsEnough(detail);
		if (in != null) {
			OutOrder order = this.getOutOrderById(orderId);
			if (order != null) {
				detail.setOutOrderId(orderId);
				order.setOutTotalPrice(order.getOutTotalPrice()
						+ detail.getOutItemPrice() * detail.getOutCount());
				in.setCurrentCount(in.getCurrentCount() - count);
				try {
					this.dao.insert(detail);
					this.dao.update(order);
					this.dao.update(in);
					return this.SUCCEED;
				} catch (SqlErrorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#updateDetail(double,
	 * int, int, int, int, int, double)
	 */
	@Override
	public boolean updateDetail(int detailId, int count, float itemPrice) {
		// TODO Auto-generated method stub
		try {
			OutDetails detail = (OutDetails) this.dao.findById(
					OutDetails.class, detailId);

			if (detail != null) {
				OutOrder order = this.getOutOrderById(detail.getOutOrderId());
				Inventory in = this.getInventoryByDetail(detail);
				if (in != null && order != null) {

					order.setOutTotalPrice(order.getOutTotalPrice()
							- detail.getOutCount() * detail.getOutItemPrice()
							+ count * itemPrice);
					order.setOutDiscountedPrice(order.getOutTotalPrice()
							* order.getOutDiscountRate());

					in.setCurrentCount(in.getCurrentCount()
							+ detail.getOutCount() - count);
					detail.setOutItemPrice(itemPrice);
					detail.setOutCount(count);

					this.dao.update(detail);
					this.dao.update(order);
					this.dao.update(in);
					return true;
				}
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#findDetails(int)
	 */
	@Override
	public List findDetails(int orderId) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from OutDetails where outOrderId = "
					+ orderId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IOutOrderService#removeDetail(int)
	 */
	@Override
	public boolean removeDetail(int detailId) {
		// TODO Auto-generated method stub
		try {
			OutDetails detail = (OutDetails) this.dao.findById(
					OutDetails.class, detailId);
			int orderId = detail.getOutOrderId();
			OutOrder order = this.getOutOrderById(orderId);

			if (detail != null && order != null) {
				Inventory in = this.getInventoryByDetail(detail);
				if (in != null) {
					order.setOutTotalPrice(order.getOutTotalPrice()
							- detail.getOutItemPrice() * detail.getOutCount());
					in.setCurrentCount(in.getCurrentCount()
							+ detail.getOutCount());
					this.dao.delete(detail);
					this.dao.update(in);
					if (this.checkOrderHasDetail(orderId)) {
						this.dao.update(order);
					} else {
						this.dao.delete(order);
					}
					return this.TRUE;
				}

			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
