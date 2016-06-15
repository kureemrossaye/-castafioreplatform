package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import java.math.BigInteger;


@NodeEntity
public class Machinery extends BaseErpModel{
	
	private String code;
	
	private String description;
	
	private BigDecimal value;
	
	private BigInteger duration;
	
	private String finiteCapital;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigInteger getDuration() {
		return duration;
	}

	public void setDuration(BigInteger duration) {
		this.duration = duration;
	}

	public String getFiniteCapital() {
		return finiteCapital;
	}

	public void setFiniteCapital(String finiteCapital) {
		this.finiteCapital = finiteCapital;
	}
	
	public String toString(){
		return code + " - " + description;
	}

}
