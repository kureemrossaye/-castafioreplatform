package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.math.BigInteger;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.annotations.Workflow;
import org.castafiore.erp.ui.audit.AuditTable;

@NodeEntity
@Table(columns={"code", "currency", "rate"})
@Forms(name="Currency",label="Currencies",forms={
		@Form(layout="12",label="Rates History",name="Versions" ,lazy=true,groups={
				@Group(layoutData="0:0",label="History",name="History" ,impl=AuditTable.class)
		}),
		@Form(layout="8:4;8:4",label="Update",name="SalesOrder",groups={
				@Group(layoutData="0:0",label="Main",name="G1",fields={"code","currency","rate"}),
				@Group(layoutData="0:1",label="Accounts",name="G1",fields={"realisedGain","realisedLoss","unrealisedGain", "unrealisedLoss","roundingPrecision"})
				
		})
		
		
		
})
@Workflow
public class Currency extends BaseAttachementableErpModel{
	
	@Column(caption="Code",style="width:10%")
	@Field(caption="Code",required=true,validators={"unique"},updateable=false,size=10,maxLength=10)
	private String code;
	
	@Column(caption="Currency")
	@Field(caption="Currency", required=true,size=25,maxLength=25)
	private String currency;
	
	@Field(caption="Commission",type=FieldType.Float,required=true)
	@Column(caption="Rate", style="width:10%")
	private BigDecimal rate;
	
	@Field(caption="Realized Gain a/c", labelWidth="30%",fieldWidth="68%",style="width:100px")
	private String realisedGain;
	
	@Field(caption="Realized Loss a/c", labelWidth="30%",fieldWidth="68%",style="width:100px")
	private String realisedLoss;
	
	@Field(caption="Unrealized Gain a/c", labelWidth="30%",fieldWidth="68%",style="width:100px")
	private String unrealisedGain;
	
	@Field(caption="Unrealized Loss a/c", labelWidth="30%",fieldWidth="68%",style="width:100px")
	private String unrealisedLoss;
	
	@Field(caption="Rounding precision",type=FieldType.Int, labelWidth="30%",fieldWidth="68%",style="width:100px")
	private BigInteger roundingPrecision;
	
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRealisedGain() {
		return realisedGain;
	}

	public void setRealisedGain(String realisedGain) {
		this.realisedGain = realisedGain;
	}

	public String getRealisedLoss() {
		return realisedLoss;
	}

	public void setRealisedLoss(String realisedLoss) {
		this.realisedLoss = realisedLoss;
	}

	public String getUnrealisedGain() {
		return unrealisedGain;
	}

	public void setUnrealisedGain(String unrealisedGain) {
		this.unrealisedGain = unrealisedGain;
	}

	public String getUnrealisedLoss() {
		return unrealisedLoss;
	}

	public void setUnrealisedLoss(String unrealisedLoss) {
		this.unrealisedLoss = unrealisedLoss;
	}

	public BigInteger getRoundingPrecision() {
		return roundingPrecision;
	}

	public void setRoundingPrecision(BigInteger roundingPrecision) {
		this.roundingPrecision = roundingPrecision;
	}
	
	public String toString(){
		return code + " " + currency;
	}
	
}
