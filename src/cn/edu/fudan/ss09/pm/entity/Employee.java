/**
 * 
 */
package cn.edu.fudan.ss09.pm.entity;

/**
 * @author Nicholas
 *
 */
/*==============================================================*/
/* Table: Employee                                              */
/*==============================================================*/
/*create table Employee
(
   employeeId           char(8) not null,
   name                 varchar(20),
   gender               int not null,
   email                varchar(100) not null,
   primary key (employeeId)
);
*/
public class Employee {
	private int employeeId;
	private String name;
	private int gender;
	private String email;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(String name, int gender, String email) {
		super();
		this.name = name;
		this.gender = gender;
		this.email = email;
	}

	public Employee(int employeeId, String name, int gender, String email) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.gender = gender;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name
				+ ", gender=" + gender + ", email=" + email + "]\n";
	}
	
}
