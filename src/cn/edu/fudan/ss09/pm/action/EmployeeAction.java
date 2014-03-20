package cn.edu.fudan.ss09.pm.action;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import cn.edu.fudan.ss09.pm.entity.Employee;
import cn.edu.fudan.ss09.pm.service.IEmployeeService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class EmployeeAction extends AbstractBaseAction{
	private List root;
	private Employee employee;
	private InputStream inputStream;
	
	public InputStream getResult(){
		return inputStream;
	}
	
	public String addEmployee() throws UnsupportedEncodingException{
		if(((IEmployeeService)service).addEmployee(employee.getName(), employee.getGender(), employee.getEmail()).equals("SUCCEED"))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String updateEmployee() throws UnsupportedEncodingException{
		//System.out.println(employee.getEmployeeId());
		//System.out.println(employee.getName());
		//System.out.println(employee.getGender());
		//System.out.println(employee.getEmail());
		
		if(((IEmployeeService)service).updateEmployee(employee.getEmployeeId(), employee.getName(), employee.getGender(), employee.getEmail()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String removeEmployee() throws UnsupportedEncodingException{
		if(((IEmployeeService)service).removeEmployee(employee.getEmployeeId()))
			inputStream = new ByteArrayInputStream("succeed".getBytes("UTF-8"));
		else inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
		return SUCCEED;
	}
	
	public String getAllEmployees(){
		root = ((IEmployeeService)service).getEmployees();
		if(root!=null)
			return SUCCEED;
		return FAIL;
	}


	public List getRoot() {
		return root;
	}


	public void setRoot(List root) {
		this.root = root;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
