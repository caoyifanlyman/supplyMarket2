package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.UserInfo;

public abstract class IUserService extends IService{
	//return true if the name and pwd matches
	public abstract boolean valid(String username,String password);
	//return true if success
	public abstract boolean changePwd(String username,String oldPassword,String newPassword);
	//return true if success
	public abstract boolean createUser(String username,String password,int employeeId,int privilege);
	//return true if success
	public abstract boolean removeUser(String username);
	//privilege related methods
	public abstract boolean checkPrivilege(String username, int requiredPrivilege);
	//or
	public abstract int getPrivilege(String username);
	//choose one to implement
	//check whether the use name is used.
	public abstract boolean isUserNameUsed(String username);
	//user log in
	public abstract UserInfo login(String username, String password);
	//get users under certain privilege
	@SuppressWarnings("rawtypes")
	public abstract List getUsersByPrivilege(int privilege);
}
