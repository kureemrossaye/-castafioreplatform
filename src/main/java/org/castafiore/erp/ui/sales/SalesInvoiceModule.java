package org.castafiore.erp.ui.sales;

import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class SalesInvoiceModule extends AbstractEXModule{

	public SalesInvoiceModule() {
		super("Sales Invoice", "Sales Invoice");
		
		GridFrame frame = new GridFrame(SalesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(SalesOrder.STATUS_SALES_INVOICE));
		addChild(frame,"0:0");
	}

}
