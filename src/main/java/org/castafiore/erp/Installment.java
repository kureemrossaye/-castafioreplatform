package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.math.BigInteger;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"num", "type", "percent", "expDays"})
public class Installment extends BaseErpModel{
	
	
	@Field(caption="Payment",type=FieldType.lookup,lookupModel=PaymentMethod.class,required=true)
	@Column(caption="Payment",style="width:40%")
	private SalesOrder order;
	
	@Field(caption="Num",type=FieldType.alphaNumeric,min=1,max=100,required=true)
	@Column(caption="Num",style="width:10%")
	private BigInteger num;
	
	@Field(caption="Type",type=FieldType.lookup,lookupModel=PaymentType.class,required=true)
	@Column(caption="Type",style="width:40%")
	private PaymentType type;
	
	@Field(caption="Percent",type=FieldType.Float,min=0,max=100,required=true)
	@Column(caption="Percent",style="width:20%")
	private BigDecimal percent;
	
	@Field(caption="Expired in :",type=FieldType.Int,min=0)
	@Column(caption="Expired in",style="width:30%")
	private BigInteger expDays;

	public BigInteger getNum() {
		return num;
	}

	public void setNum(BigInteger num) {
		this.num = num;
	}

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public BigInteger getExpDays() {
		return expDays;
	}

	public void setExpDays(BigInteger expDays) {
		this.expDays = expDays;
	}

	public SalesOrder getOrder() {
		return order;
	}

	public void setOrder(SalesOrder order) {
		this.order = order;
	}
	
	
	

}
