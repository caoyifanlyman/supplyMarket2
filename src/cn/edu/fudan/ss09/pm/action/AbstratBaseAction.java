/**
 * 
 */
package cn.edu.fudan.ss09.pm.action;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.ss09.pm.service.IService;

/**
 * @author Nicholas
 *
 */
@SuppressWarnings("serial")
public abstract class AbstratBaseAction extends ActionSupport{
	protected IService service;
	protected final String SUCCEED = "SUCCEED";
	protected final String FAIL = "FAIL";
	protected final String ERROR = "ERROR";
	protected final String NOT_EXIST = "NOT_EXIST";

	public IService getService() {
		return service;
	}

	public void setService(IService service) {
		this.service = service;
	}
}
