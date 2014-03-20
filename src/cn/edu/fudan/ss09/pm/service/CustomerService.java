/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Customer;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 *
 */
@SuppressWarnings("rawtypes")
public class CustomerService extends ICustomerService {

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.CustomerIService#addCustomer(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addCustomer(int employeeId,String customerName,
			int customerGender, String customerEmail, String customerPhone,
			String customerAddress) {
		// TODO Auto-generated method stub
		if(customerName==null||customerGender<0||customerEmail==null||customerPhone==null){
			return "ILLEGAL";
		}
		Customer c = new Customer(employeeId,customerName,customerGender,customerEmail,customerPhone,customerAddress);
		
		try {
			this.dao.insert(c);
			return "SUCCEED";
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAIL";
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.CustomerIService#removeCustomer(java.lang.String)
	 */
	@Override
	public boolean removeCustomer(int customerId) {
		// TODO Auto-generated method stub
		if(customerId<0){
			return this.FALSE;
		}
		try {
			Customer cust = (Customer) this.dao.findById(Customer.class, customerId);
			if(cust!=null){
				this.dao.delete(cust);
				return this.TRUE;
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.CustomerIService#updateCustomer(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updateCustomer(int customerId,int employeeId, String customerName,
			int customerGender, String customerEmail, String customerPhone,
			String customerAddress){
		// TODO Auto-generated method stub
		if(customerId<0||customerName==null||customerGender<0||customerEmail==null||customerPhone==null){
			return this.FALSE;
		}
		Customer cust = null;
		try {
			cust = (Customer) this.dao.findById(Customer.class,customerId);
			if(cust!=null){
				cust.setCustomerAddress(customerAddress);
				cust.setCustomerEmail(customerEmail);
				cust.setCustomerGender(customerGender);
				cust.setCustomerName(customerName);
				cust.setCustomerPhone(customerPhone);
				cust.setEmployeeId(employeeId);
				this.dao.update(cust);
				return true;
			}
		} catch (SqlErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.CustomerIService#getCustomers()
	 */
	@Override
	public List getCustomers() {
		// TODO Auto-generated method stub
		try {
			List customers = this.dao.findAll(Customer.class);
			return customers;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.CustomerIService#findCustomers(java.lang.String[])
	 */
	@Override
	public List findCustomers(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
