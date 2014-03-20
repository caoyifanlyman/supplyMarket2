package cn.edu.fudan.ss09.pm.service;

import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class ICustomerService extends IService{
	//return customer id if success or null if failure
	public abstract String addCustomer(int employeeId,String customerName,int customerGender,String customerEmail,String customerPhone,String customerAddress);
	//return true if success
	public abstract boolean removeCustomer(int customerId);
	//return true if success
	public abstract boolean updateCustomer(int customerId,int employeeId,String customerName,int customerGender,String customerEmail,String customerPhone,String customerAddress);
	//return the list of all customers
	public abstract List getCustomers();
	//optional: search for certain customer, might be done in js
	//the args are going to be defined
	public abstract List findCustomers(String args[]);
}
