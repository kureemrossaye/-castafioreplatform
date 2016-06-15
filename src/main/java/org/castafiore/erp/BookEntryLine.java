package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"account", "debit", "credit", "description"})
public class BookEntryLine extends BaseErpModel{
	
	
	@Field(caption="Account",required=true,type=FieldType.lookup,lookupModel=Account.class)
	@Column(caption="Account" ,style="width:40%")
	private Account account;
	
	@Field(caption="Debit",required=true,type=FieldType.money)
	@Column(caption="Debit",style="width:10%")
	private BigDecimal debit;
	
	@Field(caption="Credit",required=true,type=FieldType.money)
	@Column(caption="Credit",style="width:10%")
	private BigDecimal credit;
	
	@Field(caption="Note",type=FieldType.textArea)
	@Column(caption="Note",style="width:40%")
	private String description;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
