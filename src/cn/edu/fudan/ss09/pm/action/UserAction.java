package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

import cn.edu.fudan.ss09.pm.entity.UserInfo;
import cn.edu.fudan.ss09.pm.service.IUserService;

public class UserAction extends AbstractBaseAction {
	private List root;
	private UserInfo userinfo;
	private String password;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String addUser() throws UnsupportedEncodingException{
		if(((IUserService)service).createUser(userinfo.getUsername(),userinfo.getPassword(),userinfo.getEmployeeId(),userinfo.getPrivilege()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeUser() throws UnsupportedEncodingException{
		if(((IUserService)service).removeUser(userinfo.getUsername()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
		
	public String getAllUsers(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		UserInfo ui = (UserInfo)session.get("userinfo");
		root = ((IUserService)service).getUsersByPrivilege(ui.getPrivilege());
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}
	
	public String login() throws UnsupportedEncodingException{
		UserInfo ui = ((IUserService) service).login(userinfo.getUsername(),userinfo.getPassword());
		if(ui!=null){
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			session.put("userinfo", ui);
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
			return SUCCEED;
		}
		inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return FAIL;
	}
	
	public String changePwd()throws UnsupportedEncodingException{
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		UserInfo ui = (UserInfo)session.get("userinfo");
		//do not update session
		if(((IUserService)service).changePwd(ui.getUsername(),userinfo.getPassword(),password))
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

	public UserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(UserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
