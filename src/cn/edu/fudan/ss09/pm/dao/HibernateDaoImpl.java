package cn.edu.fudan.ss09.pm.dao;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

@SuppressWarnings({"unchecked","rawtypes"})
public class HibernateDaoImpl extends HibernateDaoSupport implements
		IHibernateDao {
	/**
	 * 
	 */
	public void insert(Object transientObject) throws SqlErrorException {
		try {
			getHibernateTemplate().save(transientObject);
			System.out.println("MOde:"+getHibernateTemplate().getFlushMode());
			this.flush();
		} catch (DataAccessException e) {
			System.out.println("INSERT ERROR..");
			throw new SqlErrorException("Hibernate "+ e.getMessage(),
					e.getCause());
		}
	}

	/**
	 *
	 */
	public void delete(Object transientObject) throws SqlErrorException {
		try {
			this.getHibernateTemplate().delete(transientObject);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate" + e.getMessage(),
					e.getCause());
		}
	}

	/**
	 * 
	 */
	public void update(Object transientObject) throws SqlErrorException {
		try {
			System.out.println("update");
			getHibernateTemplate().update(transientObject);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate" + e.getMessage(),
					e.getCause());
		}
	}

	/**
	 * 
	 */
	

	public Object findById(Class className, Serializable pk)
			throws SqlErrorException {
		Object obj;
		try {
			obj = this.getHibernateTemplate().get(className, pk);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate " + className.getName()
					+ " " + e.getMessage(), e.getCause());
		}
		return obj;
	}

	/**
	 * 
	 */
	public List findByExample(Object transientObject) throws SqlErrorException {
		List list;
		try {
			list = this.getHibernateTemplate().findByExample(transientObject);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate "
					+ transientObject.getClass().getName() + " "
					+ e.getMessage(), e.getCause());
		}
		return list;
	}

	/**
	 * 
	 */
	public List findByProperty(String tableModel, String propertyName,
			Object value) throws SqlErrorException {
		List list;
		String queryString = "from " + tableModel + " as model where model."
				+ propertyName + "= ?";
		try {
			list = getHibernateTemplate().find(queryString, value);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate " + tableModel + " "
					+ e.getMessage(), e.getCause());
		}
		return list;
	}

	/**
	 * 
	 */
	public List findAll(Class tableModel) throws SqlErrorException {
		List list;
		try {
			System.out.println("Find All....");
			list = getHibernateTemplate().loadAll(tableModel);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate  " + tableModel.getName()
					+ " " + e.getMessage(), e.getCause());
		}
		return list;
	}

	/**
	 * 
	 */
	public List find(String Hql) throws SqlErrorException {
		List list=null;
		try {
			list = getHibernateTemplate().find(Hql);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate:" + Hql + "/n"
					+ e.getMessage(), e.getCause());
		}
		return list;
	}

	/**
	 * 
	 */
	public void saveOrUpdate(Object transientObject) throws SqlErrorException {
		try {
			this.getHibernateTemplate().saveOrUpdate(transientObject);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate "
					+ transientObject.getClass().getName() + ""
					+ e.getMessage(), e.getCause());
		}
	}

	/**
	 * 
	 */
	public void batchSaveOrUpdate(Collection transientObjects)
			throws SqlErrorException {
		try {
			this.getHibernateTemplate().saveOrUpdateAll(transientObjects);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate " + e.getMessage(),
					e.getCause());
		}
	}

	/**
	 * 
	 */

	public void refresh(Object transientObject) throws SqlErrorException {
		try {
			this.getHibernateTemplate().refresh(transientObject);
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate "
					+ transientObject.getClass().getName() + ""
					+ e.getMessage(), e.getCause());
		}
	}

	/**
	 *
	 */
	public void flush() throws SqlErrorException {
		try {
			this.getHibernateTemplate().flush();
		} catch (DataAccessException e) {
			throw new SqlErrorException("Hibernate " + e.getMessage(),
					e.getCause());
		}
	}

	/**
	 *
	 */
	
	public HibernateTemplate getHibernate() {
		return this.getHibernateTemplate();
	}

	public int updateByHql(String queryString) {
		return getHibernateTemplate().bulkUpdate(queryString);
	}

	public int updateByHql(String queryString, Object[] params) {
		return getHibernateTemplate().bulkUpdate(queryString, params);
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.dao.IHibernateDao#getExactSession()
	 */
	@Override
	public Session getExactSession() {
		// TODO Auto-generated method stub
		return this.getSession();
	}
}
