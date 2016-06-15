package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.sales.QuotationsModule;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXRetailSalesModule extends AbstractEXModule{

	public EXRetailSalesModule() {
		super("EXRetailSalesModule", "Sales orders", "icon-barcode", new String[]{"Sales", "Sales order"}, "12");
		
		
		QuotationsModule sales = new QuotationsModule();
		
		
		EXPanel panel = new EXPanel("panel", "Sales order");
		panel.setBody(sales);
		addChild(panel,"0:0");
	}

}
