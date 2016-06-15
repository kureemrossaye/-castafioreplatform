package org.castafiore.erp.ui.purchases;

import org.castafiore.erp.PurchasesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class PurchasesOrderModule extends AbstractEXModule{

	public PurchasesOrderModule() {
		super("PurchasesOrderModule", "Purchases Order", "", new String[]{}, "12");
		
		GridFrame frame = new GridFrame(PurchasesOrder.class);
		frame.addActionInterceptor(new StatusActionInterceptor(PurchasesOrder.STATUS_PURCHASE_ORDER));
		
		addChild(frame, "0:0");
		//
		//1 create new order from scratch
		//2 create new order from quotation
		//
	}

}
