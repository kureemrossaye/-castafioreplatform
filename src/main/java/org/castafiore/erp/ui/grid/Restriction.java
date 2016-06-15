package org.castafiore.erp.ui.grid;

import java.util.LinkedHashMap;
import java.util.Map;

public class Restriction {
	
	private String field;
	
	private Map<String, Object> params = new LinkedHashMap<String, Object>();
	

	
	

	public String getField() {
		return field;
	}





	public void setField(String field) {
		this.field = field;
	}





	public Map<String, Object> getParams() {
		return params;
	}





	public void setParams(Map<String, Object> params) {
		this.params = params;
	}





	public Restriction setParam(String field, Object param){
		params.put(field, param);
		return this;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
