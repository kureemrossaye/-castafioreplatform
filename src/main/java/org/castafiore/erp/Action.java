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
@Table(columns={"name", "label", "permission", "actionDetail"})
@Forms(name="Action",label="Action",forms={
		@Form(layout="12",label="Actions",name="Action",groups={
				@Group(name="G1",label="Group",layoutData="0:0",fields={"name", "label","permission", "actionDetail"})
		})
})
@DropDown(columns={"name", "label"},defaultSearch={})
public class Action extends BaseErpModel {

	@Field(caption="Name")
	@Column(caption="Name",style="15%")
	private String name;

	@Field(caption="Label")
	@Column(caption="Label",style="30%")
	private String label;

	@Field(caption="Permission")
	@Column(caption="Permission",style="15%")
	private String permission;

	@Field(caption="Action event",style="height:250px",type=FieldType.textArea)
	@Column(caption="Action" ,style="40%")
	private String actionDetail;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getActionDetail() {
		return actionDetail;
	}

	public void setActionDetail(String actionDetail) {
		this.actionDetail = actionDetail;
	}
	
	public String toString(){
		return name + " " + label;
	}

}
