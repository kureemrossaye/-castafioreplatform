package org.castafiore.erp.ui.purchases;

import org.castafiore.erp.PurchasesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class PurchasesInvoiceModule extends AbstractEXModule{

	public PurchasesInvoiceModule() {
		super("Purchases Invoice", "Purchases Invoice");
		
		GridFrame frame = new GridFrame(PurchasesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(PurchasesOrder.STATUS_PURCHASE_INVOICE));
		addChild(frame,"0:0");
	}

}
