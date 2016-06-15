package org.castafiore.erp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Interface strategy on transactions that are vatable
 * @author acer
 *
 */
public interface Vatable {
	
	public Date getDate();
	
	public String getReferenceNumber();
	
	public String getDocumentType();
	
	public BigDecimal getTotal();
	
	public BigDecimal getTotalVat();
	
	public BigInteger getStatus();

}
