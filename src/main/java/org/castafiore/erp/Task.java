package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;


import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Form;
import org.castafiore.erp.annotations.Forms;
import org.castafiore.erp.annotations.Group;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.annotations.Workflow;
import org.castafiore.erp.ui.attachements.Attachements;
import org.castafiore.erp.ui.attachements.CommentsTable;
import org.castafiore.erp.ui.audit.AuditTable;

@NodeEntity
@Forms(name="Task",label="Tasks",forms={
		@Form(name="Task",label="Task",layout="12",groups={
				@Group(name="G1",label="Tasks",layoutData="0:0",fields={"activity", "description", "startDate", "dueDate", "assignedTo", "percentCompleted",  "title"})
		}),
		@Form(layout="12",label="Versions",name="Versions" ,lazy=true,groups={
				@Group(layoutData="0:0",label="Versions",name="Versions" ,impl=AuditTable.class)
		}),
		@Form(layout="12",label="Attachements",name="Attachements" ,groups={
				@Group(layoutData="0:0",label="Attachements",name="Attachements" ,impl=Attachements.class)
		}),
		@Form(layout="12",label="Comments",name="Comments" ,groups={
				@Group(layoutData="0:0",label="Comments",name="Comments" ,impl=CommentsTable.class)
		})
})
@Table(columns={"title", "startDate", "dueDate", "assignedTo", "percentCompleted"})
@Workflow
public class Task extends BaseAttachementableErpModel{
	
	@Field(caption="Title")
	@Column(caption="Title")
	private String title;
	
	@Field(caption="Note",type=FieldType.textArea,style="height:50px")
	private String description;
	
	@Field(caption="Start date",type=FieldType.date, required=true)
	@Column(caption="Start date")
	private Date startDate;
	
	@Field(caption="End date",type=FieldType.date, required=true)
	@Column(caption="End date")
	private Date dueDate;
	
	@Field(caption="Assigned to",type=FieldType.lookup,required=true,lookupModel=Employee.class)
	@Column(caption="Assigned to")
	private Employee assignedTo;
	
	@Field(caption="% completed",type=FieldType.Float,required=true)
	@Column(caption="% complete")
	private BigDecimal percentCompleted;
	
	@Field(caption="Project",type=FieldType.lookup,required=true,lookupModel=Activity.class)
	@Column(caption="Project")
	private Activity activity;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Employee getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Employee assignedTo) {
		this.assignedTo = assignedTo;
	}

	public BigDecimal getPercentCompleted() {
		return percentCompleted;
	}

	public void setPercentCompleted(BigDecimal percentCompleted) {
		this.percentCompleted = percentCompleted;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	

}
