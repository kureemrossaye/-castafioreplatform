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

@Forms(name="Process",label="Process",forms={
		@Form(name="Process",label="Process",layout="12",groups={
				@Group(name="G1",label="G1",layoutData="0:0",fields={"target","action"})
		})
})
@Table(columns={"target","action"})
@Search(defaultProperties={"target","action"})
@DropDown(columns={"target","action"},defaultSearch={"target","action"})

public class Process extends BaseErpModel{
	
	
	private String label;
	
	@Column(caption="Next State",style="width:50%")
	@Field(caption="Next state",type=FieldType.lookup,lookupModel=State.class,required=true)
	private State target;
	
	@Column(caption="Action",style="50%")
	@Field(caption="Action",type=FieldType.lookup,lookupModel=Action.class,required=true)
	private Action action;

	public State getTarget() {
		return target;
	}

	public void setTarget(State target) {
		this.target = target;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
