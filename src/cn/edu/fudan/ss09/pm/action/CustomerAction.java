package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Customer;
import cn.edu.fudan.ss09.pm.service.ICustomerService;

public class CustomerAction extends AbstractBaseAction{
	private List root;
	private Customer customer;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String addCustomer() throws UnsupportedEncodingException{
		if(((ICustomerService)service).addCustomer(customer.getEmployeeId(),customer.getCustomerName(),customer.getCustomerGender(),customer.getCustomerEmail(),customer.getCustomerPhone(),customer.getCustomerAddress()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String updateCustomer() throws UnsupportedEncodingException{
		if(((ICustomerService)service).updateCustomer(customer.getCustomerId(), customer.getEmployeeId(),customer.getCustomerName(),customer.getCustomerGender(),customer.getCustomerEmail(),customer.getCustomerPhone(),customer.getCustomerAddress()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeCustomer() throws UnsupportedEncodingException{
		if(((ICustomerService)service).removeCustomer(customer.getCustomerId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String getAllCustomers(){
		root = ((ICustomerService)service).getCustomers();
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
