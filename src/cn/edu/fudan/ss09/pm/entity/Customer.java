package cn.edu.fudan.ss09.pm.entity;


/*==============================================================*/
/* Table: Customer                                              */
/*==============================================================*/
/*create table Customer
(
   customerId           char(8) not null,
   employeeId           char(8) not null,
   customerName         varchar(20) not null,
   customerGender       int not null,
   customerEmail        varchar(100) not null,
   customerPhone        varchar(30) not null,
   customerAddress      varchar(200) not null,
   primary key (customerId)
);
*/

public class Customer {
	private int customerId;
	private int employeeId;
	private String customerName;
	private int customerGender;
	private String customerEmail;
	private String customerPhone;
	private String customerAddress;
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(int employeeId,String customerName, int customerGender,
			String customerEmail, String customerPhone, String customerAddress) {
		super();
		this.employeeId = employeeId;
		this.customerName = customerName;
		this.customerGender = customerGender;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
	}

	public Customer(int customerId, int employeeId, String customerName,
			int customerGender, String customerEmail, String customerPhone,
			String customerAddress) {
		super();
		this.customerId = customerId;
		this.employeeId = employeeId;
		this.customerName = customerName;
		this.customerGender = customerGender;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getCustomerGender() {
		return customerGender;
	}
	public void setCustomerGender(int customerGender) {
		this.customerGender = customerGender;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
}
