/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;
import cn.edu.fudan.ss09.pm.entity.OutDetails;

/**
 * @author Nicholas
 *
 */
@SuppressWarnings("rawtypes")
public abstract class IOutOrderService extends IService{
	//return order id if success or null if failure
	public abstract String addOrder(int customerId,List<OutDetails> inDetails,float discountedPrice);
	//return true if success
	//should remove all related order details
	public abstract boolean removeOrder(int orderId);
	//return true if success
	public abstract boolean updateOrder(int orderId,int customerId);
	//return the list of all orders
	public abstract List getOrders();
	//optional: search for certain order, might be done in js
	//the args are going to be defined
	public abstract List getOrdersByCustomerId(int customerId);
	
	//return detail id if success or null if failure
	public abstract String addDetail(String isbn,int edition,int impression,int orderId,int warehouseId,int count,float itemPrice);
	//return true if success
	public abstract boolean removeDetail(int detailId);
	//return true if success
	public abstract boolean updateDetail(int detailId,int count,float itemPrice);
	//find the certain in details related to an in order
	public abstract List findDetails(int orderId);
}
