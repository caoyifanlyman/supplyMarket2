package cn.edu.fudan.ss09.pm.service;
import java.sql.Date;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class IOrderService extends IService{
	//return order id if success or null if failure
	abstract String addOrder(String customerId,Date orderDate,double totalPrice,double discountRate,double discountedPrice);
	//return true if success
	//should remove all related order details
	abstract boolean removeOrder(String orderId);
	//return true if success
	abstract boolean updateOrder(String customerId,Date orderDate,double totalPrice,double discountRate,double discountedPrice);
	//return the list of all orders
	abstract List getOrders();
	//optional: search for certain order, might be done in js
	//the args are going to be defined
	abstract List findOrders(String args[]);
	
	//return detail id if success or null if failure
	abstract String addDetail(double isbn,int edition,int impression,String orderId,String warehouseId,int count,double itemPrice);
	//return true if success
	abstract boolean removeDetail(String detailId);
	//return true if success
	abstract boolean updateDetail(double isbn,int edition,int impression,String orderId,String warehouseId,int count,double itemPrice);
	//find the certain in details related to an in order
	abstract List findDetails(String orderId);
}
