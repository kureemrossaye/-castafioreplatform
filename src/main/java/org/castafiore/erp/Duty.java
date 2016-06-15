package org.castafiore.erp;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Duty extends BaseErpModel{
	
	private String code;
	
	private String description;
	
	private Account account;
	
	private String finiteCapital;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFiniteCapital() {
		return finiteCapital;
	}

	public void setFiniteCapital(String finiteCapital) {
		this.finiteCapital = finiteCapital;
	}
	
	public String toString(){
		return code + " - " + description;
	}
	

}
