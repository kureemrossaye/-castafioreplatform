package org.castafiore.erp;


import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"code","description"})
@Forms(label="Main",name="Main",forms={
		@Form(layout="12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"code","description", "account"})
		})
})
@DropDown(columns={"code", "description", "account"},defaultSearch={})
@Search(defaultProperties={"code", "description", "account"})
public class PaymentType extends BaseErpModel{
	
	@Field(caption="Code")
	@Column(caption="Code")
	private String code;
	
	@Field(caption="Description",type=FieldType.textArea)
	@Column(caption="Description")
	private String description;
	
	@Field(caption="Account",type=FieldType.lookup,lookupModel=Account.class)
	@Column(caption="Account")
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	public String toString(){
		return code + " - " + description;
	}
	

}
