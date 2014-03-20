package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Product;


public abstract class IProductService  extends IService{
	//the inventory page might call this to check warehouse info
	public abstract Product getProduct(String isbn,int edition,int impression);
	//the inventory page might call this to check warehouse info
	@SuppressWarnings("rawtypes")
	public abstract List getAllProduct();
}