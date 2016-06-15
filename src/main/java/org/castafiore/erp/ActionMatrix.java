package org.castafiore.erp;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ActionMatrix extends BaseErpModel{
	
	private String module;
	
	private String action;
	
	private List<EmployeeMembership> matrix = new ArrayList<EmployeeMembership>();
	
	

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<EmployeeMembership> getMatrix() {
		return matrix;
	}

	public void setMatrix(List<EmployeeMembership> matrix) {
		this.matrix = matrix;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	
	

}
