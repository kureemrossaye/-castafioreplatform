package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;


@NodeEntity
public class ScheduledMachinery extends BaseErpModel{
	
	private Machinery machinery;
	
	private Date startDate;
	
	private Date endDate;
	
	private BigInteger duration;
	
	private String note;

	public Machinery getMachinery() {
		return machinery;
	}

	public void setMachinery(Machinery machinery) {
		this.machinery = machinery;
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
