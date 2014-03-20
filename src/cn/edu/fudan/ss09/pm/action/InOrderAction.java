package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.InDetails;
import cn.edu.fudan.ss09.pm.entity.InOrder;
import cn.edu.fudan.ss09.pm.service.IInOrderService;

public class InOrderAction extends AbstractBaseAction{
	private List root;
	private InOrder inorder;
	private InDetails indetails;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String addOrder() throws UnsupportedEncodingException{
		if(((IInOrderService)service).addOrder(inorder.getCustomerId(),new ArrayList(),inorder.getInDiscountRate()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeOrder() throws UnsupportedEncodingException{
		if(((IInOrderService)service).removeOrder(inorder.getInOrderId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String getAllOrders(){
		root = ((IInOrderService)service).getOrders();
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}
	
	public String getDetails(){
		root = ((IInOrderService)service).findDetails(inorder.getInOrderId());
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}
	
	public String addDetails() throws UnsupportedEncodingException{
		System.out.println(indetails.getInOrderId());
		if(((IInOrderService)service).addDetail(indetails.getISBN(),indetails.getEdition(),indetails.getImpression(),indetails.getInOrderId(),indetails.getWarehouseId(),indetails.getInCount(),indetails.getInItemPrice()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeDetails() throws UnsupportedEncodingException{
		if(((IInOrderService)service).removeDetail(indetails.getInDetailsId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String updateDetails() throws UnsupportedEncodingException{
		if(((IInOrderService)service).updateDetail(indetails.getInDetailsId(),indetails.getInCount(),indetails.getInItemPrice()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}

	public InOrder getInorder() {
		return inorder;
	}

	public void setInorder(InOrder inorder) {
		this.inorder = inorder;
	}

	public InDetails getIndetails() {
		return indetails;
	}

	public void setIndetails(InDetails indetails) {
		this.indetails = indetails;
	}

}
