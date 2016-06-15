package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;


import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.CodeFieldAction;
import org.castafiore.utils.StringUtil;

@NodeEntity
@Table(columns={"code", "percent","startDate", "endDate"})

@Forms(name="DiscountForm", label="Discount form", forms={
		@Form(name="Main",label="Main", layout="12",groups={
				@Group(name = "CodeGroup",label = "", layoutData="0:0",fields={"code", "percent", "startDate","endDate"})
		})
})

public class Discount extends BaseErpModel implements IAssociationModel{
	
	@Column(caption="Code",style="width:25%")
	@Field(caption="Code", required=true,size=8,maxLength=8, validators={"unique"},updateable=false,actions=CodeFieldAction.class, actionScope=ActionScope.create)
	private String code;
	
	
	
	@Column(caption="Percent (%)",style="width:25%")
	@Field(caption="Percent (%)", type=FieldType.Float,min=0, max=100)
	private BigDecimal percent;
	
	
	
	@Column(caption="From",style="width:25%")
	@Field(caption="From", type=FieldType.date)
	private Date startDate;
	
	@Column(caption="To",style="width:25%")
	@Field(caption="To %", type=FieldType.date)
	private Date endDate;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	@Override
	public Boolean isNull() {
		return !StringUtil.isNotEmpty(this.code);
	}
	
}
