package org.castafiore.erp.ui.purchases;

import org.castafiore.erp.PurchasesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class PurchasesReturnModule extends AbstractEXModule{

	public PurchasesReturnModule() {
		super("SalesOrderModule", "Sales Returns", "", new String[]{}, "12");
		
		GridFrame frame = new GridFrame(PurchasesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(PurchasesOrder.STATUS_PURCHASE_RETURN));
		
		addChild(frame, "0:0");

	}

}
