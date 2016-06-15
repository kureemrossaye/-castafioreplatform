package org.castafiore.erp.ui.grid;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.search.SearchEngine;
import org.castafiore.erp.search.SearchResponse;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;

public class DefaultDataLocator implements GridDataLocator {

	public DefaultDataLocator() {
		super();
	}

	@Override
	public SearchResponse loadData(Class<? extends BaseErpModel> vo,QueryParams context) {

		
		SearchResponse response = SpringUtil.getBeanOfType(SearchEngine.class).search(vo, context);
		
		
		
		for (BaseErpModel instance : context.getToConcat()) {
			response.getData().add(0, instance);
		}
		response.setCount(response.getCount() + context.getToConcat().size());
		
		return response;

	}

	@Override
	public BaseErpModel findById(Class<? extends BaseErpModel> vo, Integer id) {
		Object o = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().get(vo, id);
		return (BaseErpModel)o;
	}

	

}
