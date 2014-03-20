/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: UserInfo                                              */
/*==============================================================*/
/*create table UserInfo
(
   username             varchar(20) not null,
   employeeId           char(8),
   password             char(50) not null,
   privilege            int not null,
   primary key (username)
);
*/
public class UserInfo {
	private String username;
	private int employeeId;
	private String password;
	private int privilege;
	
	public UserInfo(String username, int employeeId, String password,
			int privilege) {
		super();
		this.username = username;
		this.employeeId = employeeId;
		this.password = password;
		this.privilege = privilege;
	}
	public UserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	
}
