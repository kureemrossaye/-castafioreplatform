package org.castafiore.erp;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class VatRegister extends BaseErpModel{
	
	private String code;
	
	private String description;
	
	private VatRegisterType type;
	
	private Account account;

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

	public VatRegisterType getType() {
		return type;
	}

	public void setType(VatRegisterType type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	

}
