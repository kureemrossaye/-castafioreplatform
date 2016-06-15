package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.ArrayList;
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
import org.castafiore.erp.ui.form.EntityFieldDecorator;

@NodeEntity
@Forms(name="Workflow",label="Workflow",forms={
		@Form(name="Workflow",label="Workflow",layout="12;12",groups={
				@Group(layoutData="0:0",name="G1",label="G1",fields={"entity"}),
				@Group(layoutData="0:1",name="G2",label="G2",fields={"states"},displayType=DisplayType.TABLE_FORM)
		})
})
@Table(columns={"entity"})
@DropDown(columns={"entity"},defaultSearch={"entity"})
@Search(defaultProperties={"entity"})
public class Workflow extends BaseErpModel{
	
	@Column(caption="Entity",style="100%")
	@Field(caption="Entity",type=FieldType.select,items={},required=true,fieldInterceptor=EntityFieldDecorator.class)
	private String entity;
	
	@Field(caption="State",type=FieldType.table,lookupModel=State.class)
	private List<State> states = new ArrayList<State>();
	
	

	public String getEntity() {
		return entity;
	}



	public void setEntity(String entity) {
		this.entity = entity;
	}


	public State getState(BigInteger status){
		if(status == null){
			return null;
		}
		for(State state : states){
			if(state.getValue().intValue() == status.intValue())
				return state;
		}
		return null;
	}
	
	
	public List<State> getStates() {
		return states;
	}



	public void setStates(List<State> states) {
		this.states = states;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
