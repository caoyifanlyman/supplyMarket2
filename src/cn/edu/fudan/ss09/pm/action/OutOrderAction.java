package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.OutDetails;
import cn.edu.fudan.ss09.pm.entity.OutOrder;
import cn.edu.fudan.ss09.pm.service.IOutOrderService;

public class OutOrderAction extends AbstractBaseAction{
	private List root;
	private OutOrder outorder;
	private OutDetails outdetails;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String addOrder() throws UnsupportedEncodingException{
		if(((IOutOrderService)service).addOrder(outorder.getCustomerId(),new ArrayList(),outorder.getOutDiscountRate()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeOrder() throws UnsupportedEncodingException{
		if(((IOutOrderService)service).removeOrder(outorder.getOutOrderId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String getAllOrders(){
		root = ((IOutOrderService)service).getOrders();
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}
	
	public String getDetails(){
		System.out.println(outorder.getOutOrderId());
		root = ((IOutOrderService)service).findDetails(outorder.getOutOrderId());
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}
	
	public String addDetails() throws UnsupportedEncodingException{
		System.out.println(outdetails.getOutOrderId());
		if(((IOutOrderService)service).addDetail(outdetails.getISBN(),outdetails.getEdition(),outdetails.getImpression(),outdetails.getOutOrderId(),outdetails.getWarehouseId(),outdetails.getOutCount(),outdetails.getOutItemPrice()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeDetails() throws UnsupportedEncodingException{
		if(((IOutOrderService)service).removeDetail(outdetails.getOutDetailsId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String updateDetails() throws UnsupportedEncodingException{
		if(((IOutOrderService)service).updateDetail(outdetails.getOutDetailsId(),outdetails.getOutCount(),outdetails.getOutItemPrice()))
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

	public OutOrder getOutorder() {
		return outorder;
	}

	public void setOutorder(OutOrder outorder) {
		this.outorder = outorder;
	}

	public OutDetails getOutdetails() {
		return outdetails;
	}

	public void setOutdetails(OutDetails outdetails) {
		this.outdetails = outdetails;
	}

}
