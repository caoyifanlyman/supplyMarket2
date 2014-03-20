/**
 * 
 */
package cn.edu.fudan.ss09.pm.service;

import java.util.List;

import cn.edu.fudan.ss09.pm.entity.Employee;
import cn.edu.fudan.ss09.pm.exception.SqlErrorException;

/**
 * @author Nicholas
 *
 */
@SuppressWarnings("rawtypes")
public class EmployeeService extends IEmployeeService {

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IEmployeeService#addEmployee(java.lang.String, int, java.lang.String)
	 */
	@Override
	public String addEmployee(String name, int gender, String email) {
		// TODO Auto-generated method stub
		if(name==null||email==null||gender<0){
			return "FAIL";
		}
		Employee em = new Employee(name,gender,email);
		try {
			System.out.println(em);
			this.dao.insert(em);
			System.out.println(em);
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";
		}
		return "SUCCEED";
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IEmployeeService#removeEmployee(java.lang.String)
	 */
	@Override
	public boolean removeEmployee(int employeeId) {
		// TODO Auto-generated method stub
		if(employeeId<0){
			return false;
		}
		try {
			Employee  em = (Employee) this.dao.findById(Employee.class, employeeId);
			
			if(em!=null){
				System.out.println("employee:"+em);
				this.dao.delete(em);
				return true;
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IEmployeeService#updateEmployee(java.lang.String, int, java.lang.String)
	 */
	@Override
	public boolean updateEmployee(int employeeId,String name, int gender, String email) {
		// TODO Auto-generated method stub
		if(employeeId<0||name==null||gender<0||email==null){
			return false;
		}
		
		try {
			Employee  em = (Employee) this.dao.findById(Employee.class, employeeId);
			if(em!=null){
				em.setEmail(email);
				em.setGender(gender);
				em.setName(name);
				System.out.println("employee:"+em);
				this.dao.saveOrUpdate(em);
				return true;
			}
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IEmployeeService#getEmployees()
	 */
	@Override
	public List getEmployees() {
		// TODO Auto-generated method stub
		try {
			List l = this.dao.findAll(Employee.class);
			System.out.println(l);
			return l;
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.fudan.ss09.pm.service.IEmployeeService#findEmployees(java.lang.String[])
	 */
	@Override
	public List findEmployees(String[] args) {
		// TODO Auto-generated method stub
		try {
			return this.dao.find("from Employee");
		} catch (SqlErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
