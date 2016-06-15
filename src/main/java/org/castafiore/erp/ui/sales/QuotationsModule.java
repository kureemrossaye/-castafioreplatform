package org.castafiore.erp.ui.sales;

import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class QuotationsModule extends AbstractEXModule{

	public QuotationsModule() {
		super(QuotationsModule.class.getName(),"Quotations", "",new String[]{},"12");
		GridFrame frame = new GridFrame(SalesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(SalesOrder.STATUS_QUOTATIONS));
		addChild(frame,"0:0");

	}

}
