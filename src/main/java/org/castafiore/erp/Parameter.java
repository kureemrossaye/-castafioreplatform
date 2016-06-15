package org.castafiore.erp;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.neo4j.ogm.annotation.NodeEntity;

@Forms(name="Parameters",label="Parameters",forms={
		@Form(name="Parameters",label="Parameters",layout="12",groups={
				@Group(name="Main",label="Main",layoutData="0:0",fields={"key", "value", "category", "help"})
		})
})
@Table(columns={"key", "value", "category", "help"})
@DropDown(columns={"key", "value", "category", "help"},defaultSearch={})
@NodeEntity
public class Parameter extends BaseErpModel {
	
	@Column(caption="Key")
	@Field(caption="Key")
	private String key;
	
	@Column(caption="Value")
	@Field(caption="Value")
	
	private String value;
	
	@Column(caption="Help")
	@Field(caption="Help")
	private String help;
	
	@Column(caption="Category")
	@Field(caption="Category")
	private String category;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
