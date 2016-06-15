package org.castafiore.erp;


import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
@NodeEntity
public class EmployeeMembership extends BaseErpModel {


	private EmployeeRole role;
	
	private EmployeeGroup grp;
	
	private Employee employee;

	public EmployeeRole getRole() {
		return role;
	}

	public void setRole(EmployeeRole role) {
		this.role = role;
	}

	public EmployeeGroup getGrp() {
		return grp;
	}

	public void setGrp(EmployeeGroup grp) {
		this.grp = grp;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
	
	
	
}
