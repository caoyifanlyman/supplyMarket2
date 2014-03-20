/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.sql.Date;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.InDetails;
import cn.edu.fudan.ss09.pm.entity.InOrder;
import cn.edu.fudan.ss09.pm.entity.Inventory;
import cn.edu.fudan.ss09.pm.entity.Product;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 * 
 */
@SuppressWarnings("rawtypes")
public class InOrderService extends IInOrderService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#addOrder(int,
	 * java.util.List, double)
	 */
	@Override
	public String addOrder(int customerId, List<InDetails> inDetails,
			float discountedPrice) {

		// int customerId, Date inOrderDate, float inTotalPrice,
		// double inDiscountRate, float inDiscountedPrice

		Date date = new Date(System.currentTimeMillis());
		float inTotalPrice = 0;
		for (InDetails d : inDetails) {
			inTotalPrice += d.getInCount() * d.getInItemPrice();
		}

		float inDiscountedPrice = inTotalPrice * discountedPrice;
		// int customerId, Date inOrderDate, float inTotalPrice,
		// double inDiscountRate, float inDiscountedPrice
		InOrder order = new InOrder(customerId, date, inTotalPrice,
				discountedPrice, inDiscountedPrice);

		try {
			this.dao.insert(order);
			int orderId = order.getInOrderId();
			Inventory in = null;
			for (InDetails d : inDetails) {
				d.setInOrderId(orderId);
				this.dao.insert(d);
				if ((in = checkIsExist(d)) == null) {
					/*
					 * inventoryId INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
					 * ISBN char(13) not null, edition int not null, impression
					 * int not null, warehouseId INTEGER UNSIGNED NOT NULL,
					 * currentCount int not null, countDate date not null,
					 * 
					 * public Inventory(String iSBN, int edition, int
					 * impression, int warehouseId, int currentCount, Date
					 * countDate)
					 */
					in = new Inventory(d.getISBN(), d.getEdition(),
							d.getImpression(), d.getWarehouseId(),
							d.getInCount(), date);
					this.dao.insert(in);
				} else {
					in.setCurrentCount(in.getCurrentCount() + d.getInCount());
					this.dao.update(in);
				}
			}
			return this.SUCCEED;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.FAIL;
	}

	@SuppressWarnings("unchecked")
	private Inventory checkIsExist(InDetails detail) {
		/*
		 * ISBN numeric(13,0), edition int, impression int,
		 */
		try {
			System.out.println("Inventory checkIsExist 1......");
			String hql = "from Inventory where ISBN = '" + detail.getISBN()
					+ "' and edition = " + detail.getEdition()
					+ " and impression=" + detail.getImpression()
					+ " and warehouseId = " + detail.getWarehouseId();
			List<Inventory> inev = this.dao
					.find(hql);
			System.out.println("Inventory checkIsExist 2......");
			if (inev != null && inev.size() > 0) {
				return inev.get(0);
			}

		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Inventory checkIsExist 3......");
		}
		return null;
	}

	@SuppressWarnings("unused")
	private Product getProductInfo(String ISBN, int edition, int impression) {
		String hql = "from Product where ISBN = '" + ISBN + "' and edition = "
				+ edition + " and impression=" + impression;
		try {
			List l = this.dao.find(hql);
			if (l != null && l.size() > 0) {
				return (Product) l.get(0);
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#removeOrder(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean removeOrder(int orderId) {
		try {
			
			InOrder order = this.checkOrderExist(orderId);
			if(order!=null){
				List<InDetails> details = this.dao.find("from InDetails where inOrderId = "+orderId);
				for(InDetails d :details){
					this.removeDetail(d.getInDetailsId());
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
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#updateOrder(int, int)
	 */
	@Override
	public boolean updateOrder(int orderId, int customerId) {
		// TODO Auto-generated method stub
		try {
			InOrder order = (InOrder) this.dao.findById(InOrder.class, orderId);
			if (order != null) {
				order.setCustomerId(customerId);
				this.dao.update(order);
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
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#getOrders()
	 */
	@Override
	public List getOrdersByCustomerId(int cid) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from InOrder where customerId = " + cid);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#addDetail(double, int,
	 * int, int, int, int, double)
	 */
	@Override
	public String addDetail(String isbn, int edition, int impression,
			int orderId, int warehouseId, int count, float itemPrice) {
		// TODO Auto-generated method stub
		InOrder order = checkOrderExist(orderId);
		System.out.println("addDetail 1");
		if (order != null) {
			/**
			 * public InDetails(String iSBN, int edition, int impression, int
			 * inOrderId, int warehouseId, int inCount, float inItemPrice)
			 */
			System.out.println("addDetail 2");
			InDetails inDetail = new InDetails(isbn, edition, impression,
					orderId, warehouseId, count, itemPrice);
			order.setInTotalPrice(order.getInTotalPrice() + count * itemPrice);
			order.setInDiscountedPrice(order.getInTotalPrice()
					* order.getInDiscountRate());
			Inventory in = null;
			System.out.println("addDetail 3");
			try {
				if ((in = checkIsExist(inDetail)) == null) {
					System.out.println("addDetail 4");
					Date date = new Date(System.currentTimeMillis());
					in = new Inventory(inDetail.getISBN(),
							inDetail.getEdition(), inDetail.getImpression(),
							inDetail.getWarehouseId(), inDetail.getInCount(),
							date);
					this.dao.insert(in);
				} else {
					System.out.println("addDetail 5");
					in.setCurrentCount(in.getCurrentCount()
							+ inDetail.getInCount());
					this.dao.update(in);
				}

				System.out.println("addDetail 6");
				this.dao.update(order);
				this.dao.insert(inDetail);
				System.out.println("addDetail 7");
				return SUCCEED;
			} catch (SqlErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return FAIL;
	}

	private InOrder checkOrderExist(int orderId) {
		try {
			return (InOrder) this.dao.findById(InOrder.class, orderId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#removeDetail(int, int)
	 */
	@Override
	public boolean removeDetail(int detailId) {
		try {
			InDetails detail = (InDetails) this.dao.findById(InDetails.class,
					detailId);

			if (detail != null) {
				InOrder order = this.checkOrderExist(detail.getInOrderId());
				Inventory in = checkIsExist(detail);
				if (in != null && in.getCurrentCount() >= detail.getInCount()
						&& order != null) {
					in.setCurrentCount(in.getCurrentCount()
							- detail.getInCount());
					order.setInTotalPrice(order.getInTotalPrice()
							- detail.getInCount() * detail.getInItemPrice());
					order.setInDiscountedPrice(order.getInTotalPrice()
							* order.getInDiscountRate());
					this.dao.update(in);
					this.dao.delete(detail);
					if (findDetails(order.getInOrderId()) != null) {
						this.dao.update(order);
					} else {

						this.dao.delete(order);
					}
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
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#updateDetail(double,
	 * int, int, int, int, int, double) only used to change the number of
	 * product and price.
	 */
	@Override
	public boolean updateDetail(int detailId, int count, float itemPrice) {
		// TODO Auto-generated method stub
		InDetails detail = null;
		InOrder order = null;
		Inventory in = null;
		try {
			detail = (InDetails) this.dao.findById(InDetails.class, detailId);
			if (detail != null) {
				order = this.checkOrderExist(detail.getInCount());
				in = this.checkIsExist(detail);
				if (order != null && in != null) {
					in.setCurrentCount(in.getCurrentCount()
							- detail.getInCount() + count);

					order.setInTotalPrice(order.getInTotalPrice()
							- detail.getInItemPrice() * detail.getInCount()
							+ count * itemPrice);
					order.setInDiscountedPrice(order.getInTotalPrice()
							* order.getInDiscountRate());

					detail.setInCount(count);
					detail.setInItemPrice(itemPrice);

					this.dao.update(in);
					this.dao.update(order);
					this.dao.update(detail);
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
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#findDetails(int)
	 */
	@Override
	public List findDetails(int orderId) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from InDetails where inOrderId = " + orderId);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.edu.fudan.ss09.pm.service.IInOrderService#getOrders()
	 */
	@Override
	public List getOrders() {
		// TODO Auto-generated method stub
		try {
			return this.dao.findAll(InOrder.class);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
