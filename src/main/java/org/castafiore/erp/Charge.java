package org.castafiore.erp;

import java.math.BigDecimal;
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
import org.castafiore.utils.StringUtil;

@NodeEntity
@Table(columns={"code","description", "amount","vatCode", "type"})
@Forms(label="Main",name="Main",forms={
		@Form(layout="12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"code","description", "amount","vatCode", "type"})
		})
})
@DropDown(columns={"code", "description", "amount", "type"},defaultSearch={})
@Search(defaultProperties={"code", "description"})
public class Charge extends BaseErpModel implements IAssociationModel{
	
	@Field(caption="Code")
	@Column(caption="Code",style="width:15%")
	private String code;
	
	@Field(caption="Description",style="width:25%")
	@Column(caption="Description")
	private String description;
	
	@Field(caption="Amount",type=FieldType.Float)
	@Column(caption="Amount",style="width:10%")
	
	private BigDecimal amount;
	
	@Field(caption="Vat code",type=FieldType.lookup, lookupModel=Vat.class)
	@Column(caption="Vat code",style="width:20%")
	private Vat vatCode;

	@Field(caption="Type",type=FieldType.lookup, lookupModel=ChargeType.class)
	@Column(caption="Type",style="width:20%")
	private ChargeType type;
	
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Vat getVatCode() {
		return vatCode;
	}

	public void setVatCode(Vat vatCode) {
		this.vatCode = vatCode;
	}

	public ChargeType getType() {
		return type;
	}

	public void setType(ChargeType type) {
		this.type = type;
	}

	@Override
	public Boolean isNull() {
		if(!StringUtil.isNotEmpty(code) || amount== null){
			return true;
		}else{
			return false;
		}
	}
	
	
	

}
