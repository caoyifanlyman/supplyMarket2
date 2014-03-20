/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.UserInfo;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;
import cn.edu.fudan.ss09.pm.util.EncryptionUtil;

/**
 * @author Nicholas
 *
 */
public class UserService extends IUserService {

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#valid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean valid(String username, String password) {
		// TODO Auto-generated method stub
		
		return login(username,password)!=null;
	}

	@SuppressWarnings("rawtypes")
	public UserInfo login(String username, String password){
		try {
			password = EncryptionUtil.encodeByMD5(password);
			List l = this.dao.find("from UserInfo where username = '"+username+"' and password ='"+password+"'");
			if(l!=null&&l.size()==1){
				return (UserInfo) l.get(0);
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#changePwd(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean changePwd(String username, String oldPassword,
			String newPassword) {
		// TODO Auto-generated method stub
		UserInfo user = null;
		if((user = login(username,oldPassword))!=null){
			newPassword = EncryptionUtil.encodeByMD5(newPassword);
			user.setPassword(newPassword);
			try {
				this.dao.update(user);
				return this.TRUE;
			} catch (SqlErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#createUser(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean createUser(String username, String password,
			int employeeId,int privilege) {
		// TODO Auto-generated method stub
		if(username==null||password==null||privilege<0){
			return false;
		}
		password = EncryptionUtil.encodeByMD5(password);
		
		UserInfo u = new UserInfo(username,employeeId,password,privilege);
		try {
			this.dao.insert(u);
			return true;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#removeUser(java.lang.String)
	 */
	@Override
	public boolean removeUser(String username) {
		// TODO Auto-generated method stub
		try {
			UserInfo u = (UserInfo) this.dao.findById(UserInfo.class, username);
			System.out.println(u);
			if(u!=null){
				this.dao.delete(u);
				return this.TRUE;
			}
			
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#checkPrivilege(java.lang.String, int)
	 */
	@Override
	public boolean checkPrivilege(String username, int requiredPrivilege) {
		// TODO Auto-generated method stub
		try {
			UserInfo u = (UserInfo) this.dao.findById(UserInfo.class, username);
			if(u!=null){
				return u.getPrivilege()==requiredPrivilege;
			}
			
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#getPrivilege(java.lang.String)
	 */
	@Override
	public int getPrivilege(String username) {
		// TODO Auto-generated method stub
		try {
			UserInfo u = (UserInfo) this.dao.findById(UserInfo.class, username);
			if(u!=null){
				return u.getPrivilege();
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#isUserNameUsed(java.lang.String)
	 */
	@Override
	public boolean isUserNameUsed(String username) {
		// TODO Auto-generated method stub
		try {
			UserInfo u = (UserInfo) this.dao.findById(UserInfo.class, username);
			if(u!=null){
				return true;
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IUserService#getUsersByPrivilege(int)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getUsersByPrivilege(int privilege) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from UserInfo where privilege="+privilege);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
