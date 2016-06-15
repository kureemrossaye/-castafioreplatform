package org.castafiore.erp;

import java.util.ArrayList;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
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
@Table(columns={"code","description"})
@Forms(label="Main",name="Main",forms={
		@Form(layout="12;12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"code","description"}),
				@Group(layoutData="0:1",name="Categories",label="Categories",fields={"categories"},displayType=DisplayType.TABLE)
				
		})
})
@DropDown(columns={"code", "description"},defaultSearch={})
@Search(defaultProperties={"code", "description"})
public class ItemGroup extends BaseErpModel{
	
	@Field(caption="Code")
	@Column(caption="Code")
	private String code;
	
	@Field(caption="Description",type=FieldType.textArea)
	@Column(caption="Description")
	private String description;
	
	@Field(caption="Categories",type=FieldType.table,lookupModel=ItemCategory.class)
	private List<ItemCategory> categories = new ArrayList<ItemCategory>();

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
	
	
	public String toString(){
		return code;
	}

	public List<ItemCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ItemCategory> categories) {
		this.categories = categories;
	}
	
	
}
