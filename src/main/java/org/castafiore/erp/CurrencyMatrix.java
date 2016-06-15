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
@Table(columns={ "from", "to", "factor"})
@Forms(label="Currency matrix",name="CurrencyMatrix",forms={
		@Form(layout="12",name="CurrencyMatrix",label="Currency Matrix",groups={
			@Group(name="G1",fields={"from", "to", "factor"},label="Currency matrix",layoutData="0:0")
		})
})
public class CurrencyMatrix extends BaseErpModel{
	
	@Field(caption="From",type=FieldType.lookup, lookupModel=Currency.class,required=true)
	@Column(caption="From",style="width:40%")
	private Currency from;
	
	@Field(caption="To",type=FieldType.lookup, lookupModel=Currency.class,required=true)
	@Column(caption="To",style="width:40%")
	private Currency to;
	
	@Field(caption="Exchange Rate",type=FieldType.Float,required=true)
	@Column(caption="Exchange Rate",style="width:20%")
	private BigDecimal factor;

	public Currency getFrom() {
		return from;
	}

	public void setFrom(Currency from) {
		this.from = from;
	}

	public Currency getTo() {
		return to;
	}

	public void setTo(Currency to) {
		this.to = to;
	}

	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
	
	
	

}
