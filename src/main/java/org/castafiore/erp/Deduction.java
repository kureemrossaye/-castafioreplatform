package org.castafiore.erp;

import java.math.BigDecimal;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.NodeEntity;

public class Deduction extends BaseErpModel{
	
	private String code;
	
	private BigDecimal flatAmount;
	
	private BigDecimal rate;
	
	private String label;
	
	private Vat vat;
	
	private Account account;

	

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getFlatAmount() {
		return flatAmount;
	}

	public void setFlatAmount(BigDecimal flatAmount) {
		this.flatAmount = flatAmount;
	}
	
	

}
