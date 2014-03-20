package cn.edu.fudan.ss09.pm.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import cn.edu.fudan.ss09.pm.entity.UserInfo;

import com.opensymphony.xwork2.ActionContext;

public class SessionAction extends AbstractBaseAction {
	private InputStream inputStream;

	public InputStream getResult() {
		return inputStream;
	}

	public String checkLogin() throws UnsupportedEncodingException {
		System.out.println("checklogin");
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		if (session.containsKey("userinfo")) {
			inputStream = new ByteArrayInputStream(
					("" + ((UserInfo) session.get("userinfo")).getPrivilege())
							.getBytes("UTF-8"));
			System.out.println("success");
		} else
			inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

	public String logout() throws UnsupportedEncodingException {
		System.out.println("logout");
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		if (session.containsKey("userinfo")) {
			inputStream = new ByteArrayInputStream(
					("" + ((UserInfo) session.get("userinfo")).getPrivilege())
							.getBytes("UTF-8"));
			System.out.println("success");
			session.clear();
		} else
			inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}

}
