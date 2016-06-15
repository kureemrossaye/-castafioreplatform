package org.castafiore.erp;

import java.math.BigDecimal;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.ApplyChargeDecorator;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Forms(label="Fine charge",name="Fine",forms={
		@Form(layout="12",name="Fine",label="Fine Charge",groups={
				@Group(layoutData="0:0",name="G1",label="Fine charge",fields={"applyCharges","rate","when", "rateAppliedOn"})
		})
})
@Table(columns={"when", "rate","rateAppliedOn", "beforeVat"})
public class Fine extends BaseErpModel{
	
	@Field(caption="Rate apply on",type=FieldType.select,items={@Item(id="DUE",text="Due Date"), @Item(id="INVOICE",text="Invoice Date")},required=true)
	@Column(caption="Apply fine")
	private String when;

	@Field(caption="Rate to apply",type=FieldType.Float,required=true)
	@Column(caption="Rate to apply")
	private BigDecimal rate;
	
	@Field(caption="Rate calculated on ",type=FieldType.select,items={ @Item(id="TOTAL",text="Total only"),@Item(id="TOTAL_CHARGE",text= "Total + charge")},required=true)
	@Column(caption="Rate applied on ")
	private String rateAppliedOn;
	
	@Field(caption="Apply Charges",type=FieldType.Boolean,fieldInterceptor=ApplyChargeDecorator.class)
	private Boolean applyCharges;
	

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRateAppliedOn() {
		return rateAppliedOn;
	}

	public void setRateAppliedOn(String rateAppliedOn) {
		this.rateAppliedOn = rateAppliedOn;
	}

	public Boolean getApplyCharges() {
		return applyCharges;
	}

	public void setApplyCharges(Boolean applyCharges) {
		this.applyCharges = applyCharges;
	}

	
	
	

}
