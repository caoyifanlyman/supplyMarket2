package cn.edu.fudan.ss09.pm.service;

import java.util.List;
@SuppressWarnings("rawtypes")
public abstract class IEmployeeService extends IService{
	//return employee id if success or null if failure
	public abstract String addEmployee(String name,int gender,String email);
	//return true if success
	public abstract boolean removeEmployee(int employeeId);
	//return true if success
	public abstract boolean updateEmployee(int employeeId,String name,int gender,String email);
	//return the list of all employees
	public abstract List getEmployees();
	//optional: search for certain employee, might be done in js
	//the args are going to be defined
	public abstract List findEmployees(String args[]);
}
