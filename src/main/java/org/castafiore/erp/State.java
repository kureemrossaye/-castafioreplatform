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

@NodeEntity
@Table(columns={"label", "color", "value"})
@Forms(name="State",label="State",forms={
		@Form(name="State",label="State",layout="12;12",groups={
				@Group(name="G1",label="G1",layoutData="0:0",fields={"label", "color","value"}),
				@Group(name="G2",label="G2",layoutData="0:1",fields={"actions"},displayType=DisplayType.TABLE_FORM)
		})
})
@DropDown(columns={"label", "color", "value"},defaultSearch={"label", "color", "value"})
@Search(defaultProperties={"label", "color", "value"})
public class State extends BaseErpModel{
	
	@Field(caption="Value",type=FieldType.Int,min=1,required=true)
	@Column(caption="Value",style="width:10%")
	private BigInteger value;
	
	@Column(caption="Label",style="width:45%")
	@Field(caption="Label",required=true)
	private String label;
	
	@Field(caption="Color",required=true)
	@Column(caption="Color",style="width:45%")
	private String color;
	
	
	/**
	 * This fields is a configuration field about how to generate document number
	 */
	private String referenceConfig;
	
	@Field(type=FieldType.table,lookupModel=Process.class)
	private List<Process> actions = new ArrayList<Process>();
	
	@Column(caption="Top",style="width:45%")
	private BigInteger positionTop;
	
	private BigInteger positionLeft;

	

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Process> getActions() {
		return actions;
	}

	public void setActions(List<Process> actions) {
		this.actions = actions;
	}

	public BigInteger getValue() {
		return value;
	}

	public void setValue(BigInteger value) {
		this.value = value;
	}

	public BigInteger getPositionTop() {
		return positionTop;
	}

	public void setPositionTop(BigInteger positionTop) {
		this.positionTop = positionTop;
	}

	public BigInteger getPositionLeft() {
		return positionLeft;
	}

	public void setPositionLeft(BigInteger positionLeft) {
		this.positionLeft = positionLeft;
	}



	
	public Process connect(State target){
		Process process = new Process();
		process.setTarget(target);
		process.setLabel("");
		
		
		
		this.actions.add(process);
		return process;
		
	}
	
	
	public boolean isConnected(State target){
		for(Process process : actions){
			if(process.getTarget() != null && process.getTarget().getId() == target.getId()){
				return true;
			}
		}
		return false;
	}

	public String getReferenceConfig() {
		return referenceConfig;
	}

	public void setReferenceConfig(String referenceConfig) {
		this.referenceConfig = referenceConfig;
	}
	
	
	

	

}
