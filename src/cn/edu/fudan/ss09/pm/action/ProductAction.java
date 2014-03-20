package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Product;
import cn.edu.fudan.ss09.pm.service.IProductService;
import cn.edu.fudan.ss09.pm.service.IUserService;

public class ProductAction extends AbstractBaseAction{
	private List root;
	private Product product;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String getAllProduct(){
		System.out.println("getAllProduct.....");
		root = ((IProductService)service).getAllProduct();
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List getRoot() {
		return root;
	}

	public void setRoot(List root) {
		this.root = root;
	}
}
