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
import org.castafiore.erp.annotations.Search;
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"code","description"})
@Forms(label="Main",name="Main",forms={
		@Form(layout="12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",name="Main",label="Main",fields={"code","description"})
		})
})
@DropDown(columns={"code", "description"},defaultSearch={})
@Search(defaultProperties={"code", "description"})
public class CostingMethod extends BaseErpModel{
	
	@Field(caption="Code")
	@Column(caption="Code")
	private String code;
	
	@Field(caption="Description",type=FieldType.textArea)
	@Column(caption="Description")
	private String description;

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
		return description;
	}

}
