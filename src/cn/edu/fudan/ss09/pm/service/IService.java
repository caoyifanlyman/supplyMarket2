package cn.edu.fudan.ss09.pm.service;

import cn.edu.fudan.ss09.pm.dao.IHibernateDao;

public abstract class IService {
	IHibernateDao dao = null;
	protected final String SUCCEED = "SUCCEED";
	protected final String FAIL = "FAIL";
	protected final String ERROR ="ERROR";
	protected final String NOT_EXIST = "NOT_EXIST";
	protected final String ILLEGAL = "ILLEGAL";
	protected final boolean TRUE = true;
	protected final boolean FALSE = false;

	public IHibernateDao getDao() {
		return null;
	}
	public void setDao(IHibernateDao dao) {
		this.dao = dao;
	}
}
