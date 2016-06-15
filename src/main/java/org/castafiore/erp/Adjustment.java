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
import org.castafiore.erp.annotations.Workflow;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.audit.AuditTable;
import org.castafiore.erp.ui.form.CodeFieldAction;

@NodeEntity
@Table(columns={"dateOfTransaction", "fromWarehouse", "toWarehouse", "type"})
@Search(defaultProperties={"dateOfTransaction", "fromWarehouse", "toWarehouse", "type"})
@DropDown(columns={"dateOfTransaction", "fromWarehouse", "toWarehouse", "type"},defaultSearch={})
@Forms(label="Adjustments",name="Adjustment",forms={
		@Form(layout="6:6;6:6",label="Adjustment",name="Adjustment",groups={
				@Group(layoutData="0:0",name="G1",label="",fields={"code","dateOfTransaction","type"}),
				@Group(layoutData="1:0",name="G2",label="",fields={"responsible","fromWarehouse","toWarehouse"}),
				@Group(layoutData="0:1",name="G3",label="Lines",fields={"lines"},displayType=DisplayType.TABLE),
				@Group(layoutData="1:1",name="G4",label="",fields={"note"},style="margin-top:30px"),
		}),
		@Form(layout="12",label="Versions",name="Versions" ,lazy=true,groups={
				@Group(layoutData="0:0",label="Versions",name="Versions" ,impl=AuditTable.class)
		}),
		@Form(layout="12",label="Attachements",name="Attachements" ,groups={
				@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
		}),
		@Form(layout="12",label="Comments",name="Comments" ,groups={
				@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
		})
})
@Workflow
public class Adjustment extends BaseAttachementableErpModel{
	
	@Field(caption="Code",required=true,validators={"unique"},actions=CodeFieldAction.class)
	@Column(caption="Code")
	private String code;
	
	@Column(caption="Type")
	@Field(caption="Type",type=FieldType.lookup,lookupModel=AdjustmentType.class, required=true)
	private AdjustmentType type;
	
	@Column(caption="Date")
	@Field(caption="Date", type=FieldType.date, required=true)
	private Date dateOfTransaction;
	
	@Column(caption="Responsible")
	@Field(caption="Responsible",type=FieldType.lookup,lookupModel=Employee.class, required=true)
	private Employee responsible;
	
	@Column(caption="Note")
	@Field(caption="Note",type=FieldType.textArea,style="height:45px")
	private String note;
	
	@Column(caption="From")
	@Field(caption="From",type=FieldType.lookup,lookupModel=Warehouse.class)
	private Warehouse fromWarehouse;
	
	@Column(caption="To")
	@Field(caption="To",type=FieldType.lookup,lookupModel=Warehouse.class)
	private Warehouse toWarehouse;
	
	@Field(caption="Lines",type=FieldType.table,lookupModel=AdjustmentLineItem.class)
	private List<AdjustmentLineItem> lines = new ArrayList<AdjustmentLineItem>();

	public AdjustmentType getType() {
		return type;
	}

	public void setType(AdjustmentType type) {
		this.type = type;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public Employee getResponsible() {
		return responsible;
	}

	public void setResponsible(Employee responsible) {
		this.responsible = responsible;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Warehouse getFromWarehouse() {
		return fromWarehouse;
	}

	public void setFromWarehouse(Warehouse fromWarehouse) {
		this.fromWarehouse = fromWarehouse;
	}

	public Warehouse getToWarehouse() {
		return toWarehouse;
	}

	public void setToWarehouse(Warehouse toWarehouse) {
		this.toWarehouse = toWarehouse;
	}

	public List<AdjustmentLineItem> getLines() {
		return lines;
	}

	public void setLines(List<AdjustmentLineItem> lines) {
		this.lines = lines;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
