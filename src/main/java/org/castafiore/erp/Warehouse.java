package org.castafiore.erp;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Item;
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.audit.AuditTable;

@NodeEntity
@Table(columns={"code", "title", "type"})
@DropDown(columns={"code", "title", "type"},defaultSearch={"code", "title"})
@Forms(name="Warehouse",label="Warehouse",forms={
		@Form(layout="12",name="Warehouse",label="Warehouse",groups={
				@Group(layoutData="0:0",name="G1",label="Main",fields={"code", "title","type", "address"})
		}),
		@Form(layout="12",label="History",name="History" ,lazy=true,groups={
				@Group(layoutData="0:0",label="History",name="History" ,impl=AuditTable.class)
		})
})
@Search(defaultProperties={"code", "description"})
public class Warehouse extends BaseErpModel{
	
	@Column(caption="Code")
	@Field(caption="Code")
	private String code;
	
	@Column(caption="Title")
	@Field(caption="Title")
	private String title;
	
	@Field(caption="Address",type=FieldType.form,lookupModel=ContactAddress.class)
	private ContactAddress address;
	
	
	@Column(caption="Type")
	@Field(caption="Type",type=FieldType.select, items={@Item(id="STOCKING", text="Stocking"),@Item(id="INVOICING", text="Invoicing")})
	private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ContactAddress getAddress() {
		return address;
	}

	public void setAddress(ContactAddress address) {
		this.address = address;
	}

	public String toString(){
		return  code + "-" + title;
	}

}
