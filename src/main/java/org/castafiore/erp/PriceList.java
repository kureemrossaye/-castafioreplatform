package org.castafiore.erp;

import java.util.ArrayList;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;
import java.util.List;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DisplayType;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"code","description","startDate","endDate"})
@Forms(label="Price list form",name="PriceListForm",forms={
		@Form(layout="6:6;12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",label="Main",name="Main",fields={"code","description"}),
				@Group(layoutData="1:0",label="Restrictions",name="Restrictions",fields={"startDate","endDate"}),
				@Group(layoutData="0:1",label="Lines",name="Lines",fields={"items"},displayType=DisplayType.TABLE),
		})
})

@DropDown(columns={"code","description", "startDate","endDate"},defaultSearch={"code","description", "startDate","endDate"})

@Search(defaultProperties={"code"})
public class PriceList extends BaseErpModel{
	
	@Column(caption="Code",size="15%")
	@Field(caption="Code",size=8,required=true,style="width:100px")
	private String code;
	
	@Column(caption="Description", size="50%")
	@Field(caption="Description", required=true,type=FieldType.textArea,style="height:70px")
	private String description;
	
	@Field(caption="Items",type=FieldType.table,lookupModel=PriceItem.class)
	private List<PriceItem> items = new ArrayList<PriceItem>();
	
	@Column(caption="Start date", size="25%")
	@Field(caption="Start date",type=FieldType.date, required=true)
	private Date startDate;
	
	@Column(caption="End date", size="25%")
	@Field(caption="End date", type=FieldType.date)
	private Date endDate;

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

	public List<PriceItem> getItems() {
		return items;
	}

	public void setItems(List<PriceItem> items) {
		this.items = items;
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
	public String toString(){
		return code;
	}
	
	

}
