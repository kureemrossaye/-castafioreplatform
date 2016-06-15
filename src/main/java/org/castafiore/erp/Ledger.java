package org.castafiore.erp;

import java.util.ArrayList;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.List;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;

@NodeEntity

@Forms(name="LedgerForm", label="Ledger form",forms={
		@Form(name="Main",label="Main",groups={
				@Group(name="G1", label="",layoutData="0:0",fields={"code", "description", "type"})
		})
})

//@Form(properties={"code", "description", "type"})
@Table(columns={"code","description", "type"})
public class Ledger extends BaseErpModel {

	@Field(required=true, caption="Code")
	@Column(caption="Code",size="10%")
	private String code;
	
	@Field(caption="Description")
	@Column(caption="Description",size="50%")
	private String description;

	@Field(caption="Type",required=true, type=FieldType.lookup, lookupModel=LedgerType.class)
	@Column(caption="Code",size="10%")
	private LedgerType type;

	private List<AccountAssociations> accounts = new ArrayList<AccountAssociations>();

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

	public LedgerType getType() {
		return type;
	}

	public void setType(LedgerType type) {
		this.type = type;
	}

	public List<AccountAssociations> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountAssociations> accounts) {
		this.accounts = accounts;
	}

	public String toString(){
		return code + " - " + description;
	}

}
