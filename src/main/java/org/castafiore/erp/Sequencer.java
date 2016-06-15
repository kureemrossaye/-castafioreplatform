package org.castafiore.erp;

import java.math.BigInteger;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Sequencer{
	
	@GraphId
	private Long id;
	
	private String entity;
	
	private BigInteger current = BigInteger.ZERO;
	
	private BigInteger step = BigInteger.ONE;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigInteger getCurrent() {
		return current;
	}

	public void setCurrent(BigInteger current) {
		this.current = current;
	}

	public BigInteger getStep() {
		return step;
	}

	public void setStep(BigInteger step) {
		this.step = step;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	

}
