package org.castafiore.erp.ui.sales;

import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class SalesOrderModule extends AbstractEXModule{

	public SalesOrderModule() {
		super("SalesOrderModule", "Sales Order", "", new String[]{}, "12");
		
		GridFrame frame = new GridFrame(SalesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(SalesOrder.STATUS_SALES_ORDER));
		
		addChild(frame, "0:0");
		//
		//1 create new order from scratch
		//2 create new order from quotation
		//
	}

}
