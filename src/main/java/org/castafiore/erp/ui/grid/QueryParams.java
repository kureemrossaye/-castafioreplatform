package org.castafiore.erp.ui.grid;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.erp.BaseErpModel;

public class QueryParams implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<KeyValuePair> orders = new LinkedList<KeyValuePair>();
	
	private int page=0;
	
	private int pagSize=20;
	
	private String fullTextSearchTerm = null;
	
	private List<BaseErpModel> toConcat = new LinkedList<BaseErpModel>();
	
	private Boolean showAll=false;
	
	private List<BigInteger> status = new LinkedList<BigInteger>();
	
	private List<Restriction> restrictions = new LinkedList<Restriction>();
	

	public List<KeyValuePair> getOrders() {
		return orders;
	}
	
	public void addOrder(String field, String direction){
		SimpleKeyValuePair kv = new SimpleKeyValuePair(field, direction);
		orders.add(kv);
	}


	public String getFullTextSearchTerm() {
		return fullTextSearchTerm;
	}
	
	
	public void addRestriction(String field, List<BaseErpModel> in){
		for(Restriction r : restrictions){
			if(r.getField().equals(field)){
				restrictions.remove(r);
				break;
			}
		}
		
		toConcat.clear();
		showAll=false;
		
		
		Restriction restriction = new Restriction();
		restriction.setField(field);
		restriction.setParam("in", in);
		restrictions.add(restriction);
	}
	
	public void addRestriction(String field, String term){
		for(Restriction r : restrictions){
			if(r.getField().equals(field)){
				restrictions.remove(r);
				break;
			}
		}
		
		toConcat.clear();
		showAll=false;
		
		Restriction restriction = new Restriction();
		restriction.setField(field);
		restriction.setParam("like", term);
		restrictions.add(restriction);
		
	}
	
	public void addRestriction(String field, Date from, Date to){
		for(Restriction r : restrictions){
			if(r.getField().equals(field)){
				restrictions.remove(r);
				break;
			}
		}
		
		toConcat.clear();
		showAll=false;
		
		Restriction restriction = new Restriction();
		restriction.setField(field);
		restriction.setParam("from", from).setParam("to", to);
		restrictions.add(restriction);
	}
	
	public void addRestriction(String field, BigInteger from, BigInteger to){
		for(Restriction r : restrictions){
			if(r.getField().equals(field)){
				restrictions.remove(r);
				break;
			}
		}
		
		toConcat.clear();
		showAll=false;
		
		Restriction restriction = new Restriction();
		restriction.setField(field);
		restriction.setParam("from", from).setParam("to", to);
		
		restrictions.add(restriction);
	}
	
	public void addRestriction(String field, BigDecimal from, BigDecimal to){
		for(Restriction r : restrictions){
			if(r.getField().equals(field)){
				restrictions.remove(r);
				break;
			}
		}
		
		toConcat.clear();
		showAll=false;
		
		Restriction restriction = new Restriction();
		restriction.setField(field);
		restriction.setParam("from", from).setParam("to", to);
		restrictions.add(restriction);
	}

	public void setFullTextSearchTerm(String fullTextSearchTerm) {
		toConcat.clear();
		this.fullTextSearchTerm = fullTextSearchTerm;
		showAll=false;
	}
	
	public List<BaseErpModel> getToConcat(){
		return toConcat;
	}

	public int getPage() {
		return page;

	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagSize() {
		return pagSize;
	}

	public void setPagSize(int pagSize) {
		this.pagSize = pagSize;
	}

	public void setOrders(List<KeyValuePair> orders) {
		this.orders = orders;
	}
	
	
	public void addToConcat(BaseErpModel model){
		toConcat.add(model);
	}

	public Boolean getShowAll() {
		return showAll;
	}

	public void setShowAll(Boolean showAll) {
		this.showAll = showAll;
	}

	public List<BigInteger> getStatus() {
		return status;
	}

	public void setStatus(List<BigInteger> status) {
		this.status = status;
		showAll = false;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	


}
