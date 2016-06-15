package org.castafiore.erp;


import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.castafiore.utils.StringUtil;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * An account contains information that will be used in the accounting module.<br>
 * Accounts are configured in various entities in the system. e.g Customer, Vendors, Sales, Purchases, Charges.<br>
 * Actually it is configured in all entities that takes part in a financial transaction.<br>
 * These accounts help to properly make double entry in our ledger.<br>
 * The double entry is transparent to the user. <br>
 * Meaning for example, whenever there is a sales or purchase, the double entry is done automatically based on the configurations provided.<br>
 * <b>Note: <br>It is important that the configuration of accounts are done with the help of an accountant in order to properly maintain the different ledgers<b>
 * 
 * @author acer
 *
 */
@NodeEntity
@Table(columns={"code","name", "account"})
@Forms(label="Accounts",name="AccountAssociations",forms={
		@Form(name="Main",label="Main",layout="12",groups={
				@Group(layoutData="0:0",label="Main",name="G1",fields={"code","name","account"})
		})
})
public class AccountAssociations extends BaseErpModel implements IAssociationModel{
	
	@Column(caption="Code")
	@Field(caption="Code", required=true,size=8,maxLength=80,style="width:20%",validators={"unique"},actions=CodeFieldAction.class)
	private String code;
	
	@Column(caption="Name",style="width:40%")
	@Field(caption="Name", required=true,maxLength=80,validators={"unique"})
	private String name;
	
	@Column(caption="Account",style="width:40%")
	@Field(caption="Account", required=true,type=FieldType.lookup,lookupModel=Account.class)
	private Account account;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public Boolean isNull() {
		if(!StringUtil.isNotEmpty(code) || account == null){
			return true;
		}else{
			return false;
		}
	}
	
	

}
