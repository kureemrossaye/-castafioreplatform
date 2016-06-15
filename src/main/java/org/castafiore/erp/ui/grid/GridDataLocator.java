package org.castafiore.erp.ui.grid;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.search.SearchResponse;

public interface GridDataLocator {
	
	public SearchResponse loadData(Class<? extends BaseErpModel> vo, QueryParams context);
	
	public BaseErpModel findById(Class<? extends BaseErpModel> vo,Integer id);
	
}
