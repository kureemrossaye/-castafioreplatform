package org.castafiore.erp.sales;

import org.castafiore.erp.Customer;
import org.castafiore.erp.Item;
import org.castafiore.erp.Vat;
import org.castafiore.erp.VatMatrix;
import org.castafiore.persistence.Dao;
import org.hibernate.criterion.Restrictions;

public class SalesManager {
	
	private Dao dao;
	
	public Vat getAppliedVat(Customer customer, Item item){
		if(item.getVat() == null ){
			if(customer.getVat() != null)
				return customer.getVat();
			else
				return null;
		}
		
		if(customer.getVat() == null){
			if(item.getVat() != null){
				return item.getVat();
			}else{
				return null;
			}
		}
		
		if(customer.getVat() != null && item.getVat() != null){
			VatMatrix matrix = ((VatMatrix)dao.getSession().createCriteria(VatMatrix.class).add(Restrictions.eq("contactVat",customer.getVat())).add(Restrictions.eq("lineItemVat", item.getVat())).uniqueResult());
			
			if(matrix.getContactHasPriority()){
				return matrix.getContactVat();
			}else{
				return matrix.getLineItemVat();
			}
		}
		
		return null;
	}
	
	
	

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	

}
