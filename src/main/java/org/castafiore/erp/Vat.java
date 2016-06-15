package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"code", "description", "rate"},pageSize=20)
@Forms(name="VatForm",label="Vat form", forms={
		@Form(layout="12",name="Main", label="Main",groups={
				@Group(layoutData="0:0",name="G1",label="Vat code",fields={"code", "description", "rate"})
		})
})
public class Vat extends BaseErpModel{

	@Field(caption="Code",required=true,editable=true)
	@Column(caption="Code",size="15%")
	private String code;
	
	@Column(caption="Description", size="70%")
	@Field(caption="Description", type=FieldType.textArea, style="height:60px")
	private String description;
	
	@Column(caption="Rate (%)", size="15%")
	@Field(caption="Rate (%)", type=FieldType.Float,required=true,min=0,max=100)
	private BigDecimal rate;
	

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

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	
	public String toString(){
		return code;
	}
	

}
