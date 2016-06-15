package org.castafiore.erp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.annotations.Workflow;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
@Table(columns={"startDate", "label","type","manager", "creator"})
@Forms(name="Activity",label="Activity",forms={
		@Form(name="Main",label="Main",layout="12",groups={
				@Group(layoutData="0:0",name="G1",label="Main",fields={"startDate", "type","label","manager", "subscribers","creator"})
				
		})
})
@DropDown(columns={"startDate", "label","type","manager", "creator"},defaultSearch={})
@Workflow
public class Activity extends BaseErpModel {


	@Column(caption="Start date")
	@Field(caption="Start date",type=FieldType.date, required=true)
	private Date startDate;
	
	@Column(caption="Due date")
	@Field(caption="Due date",type=FieldType.date, required=true)
	private Date dueDate;
	
	@Field(caption="Directory")
	@Column(caption="Directory")
	private String directory;

	@Column(caption="Type")
	@Field(caption="Type",type=FieldType.lookup,lookupModel=ActivityType.class, required=true)
	private ActivityType type;
	
	@Column(caption="Label")
	@Field(caption="Label", required=true)
	private String label;
	
	
	@Field(caption="Manager",type=FieldType.lookup,lookupModel=Employee.class)
	@Column(caption="Manager")
	private Employee manager;
	
	@Field(caption="Subscribers",type=FieldType.multiselect,lookupModel=Employee.class)
	private List<Employee> subscribers = new ArrayList<Employee>();
	
	@Field(caption="Creator",type=FieldType.lookup, lookupModel=Employee.class)
	@Column(caption="Creator")
	private Employee creator;
	
	
	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public List<Employee> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(List<Employee> subscribers) {
		this.subscribers = subscribers;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}


	
	public String toString(){
		return label;
	}

	
	

}
