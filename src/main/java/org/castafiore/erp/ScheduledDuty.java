package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;


@NodeEntity
public class ScheduledDuty extends BaseErpModel {
	
	private Duty duty;
	
	private Employee employee;
	
	private Date startDate;
	
	private Date endDate;
	
	private BigInteger duration;
	
	private String note;

	public Duty getDuty() {
		return duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigInteger getDuration() {
		return duration;
	}

	public void setDuration(BigInteger duration) {
		this.duration = duration;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	

}
