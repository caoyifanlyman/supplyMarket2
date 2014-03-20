package cn.edu.fudan.ss09.pm.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

@SuppressWarnings("rawtypes")
public interface IHibernateDao{
	//删除记录
	public void delete(Object transientObject) throws SqlErrorException;
	//增加记录
	public void insert(Object transientObject) throws SqlErrorException;
	//保存记录
	public void saveOrUpdate(Object transientObject) throws SqlErrorException;

	public void batchSaveOrUpdate(Collection transientObjects) throws SqlErrorException;
	//更新记录
	public void update(Object transientObject) throws SqlErrorException;
	//查询记录
	public Object findById(Class className, Serializable pk) throws SqlErrorException;
	public List findByExample(Object transientObject) throws SqlErrorException;
	public List findAll(Class tableModel) throws SqlErrorException;
	public List findByProperty(String tableModel, String propertyName, Object value)  throws SqlErrorException;
	public List find(String Hql) throws SqlErrorException;
	//重新载入
	public void refresh(Object transientObject) throws SqlErrorException;
	//刷新
	public void flush() throws SqlErrorException;
	//获取模板
	public HibernateTemplate getHibernate();	
	public int updateByHql(String queryString) throws DataAccessException;
	public int updateByHql(String queryString, Object[] params) throws DataAccessException;
	
	public Session getExactSession();
}
