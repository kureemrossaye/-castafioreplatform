package org.castafiore.erp;

import java.math.BigInteger;
import org.neo4j.ogm.annotation.NodeEntity;
import java.util.Date;

public class BaseErpModel {

	private Integer id;

	private String organization;

	private Date dateCreated = new Date();

	private Date dateModified = new Date();

	private String createdBy;

	private String modifiedBy;

	private BigInteger status = BigInteger.ONE;

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public BigInteger getStatus() {
		return status;
	}

	public void setStatus(BigInteger status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

}
