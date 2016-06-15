package org.castafiore.erp.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.erp.BaseErpModel;

public class SearchResponse implements Serializable{
	
	private Integer count =0;
	
	private List<BaseErpModel> data = new ArrayList<BaseErpModel>();

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<BaseErpModel> getData() {
		return data;
	}

	public void setData(List<BaseErpModel> data) {
		this.data = data;
	}
	
	
	
	

	

}
