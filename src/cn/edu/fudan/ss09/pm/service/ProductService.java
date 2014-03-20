/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Product;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 *
 */
public class ProductService extends IProductService {

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IProductService#getAllProduct()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getAllProduct() {
		// TODO Auto-generated method stub
		try {
			return this.dao.findAll(Product.class);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IProductService#getProduct(java.lang.String, int, int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Product getProduct(String isbn, int edition, int impression) {
		// TODO Auto-generated method stub
		List l;
		try {
			l = this.dao.find("from Product where isbn = '"+isbn+"' and edition = "+edition+" and impression = "+impression);
			if(l!=null&&l.size()>0){
				return (Product) l.get(0);
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
