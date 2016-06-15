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
import org.castafiore.erp.annotations.Table;

@NodeEntity
@Table(columns={"name", "description"})
@Forms(label="Bank form",name="BankForm",forms={
		@Form(layout="12",label="Main",name="Main",groups={
				@Group(layoutData="0:0",label="Main",name="G1",fields={"name", "description"})
		})
})

@DropDown(columns={"name","description"}, defaultSearch={"name", "description"})
public class Bank extends BaseErpModel{
	
	@Field(caption="Name", required=true, validators={"unique"})
	@Column(caption="Name", size="20%")
	private String name;
	
	@Column(caption="Description", size="80%")
	@Field(caption="Description", required=true, type=FieldType.textArea, style="width:100%;height:200px")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
