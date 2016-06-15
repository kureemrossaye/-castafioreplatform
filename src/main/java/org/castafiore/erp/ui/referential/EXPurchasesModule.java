package org.castafiore.erp.ui.referential;

import org.castafiore.erp.PurchasesOrder;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXPurchasesModule extends AbstractEXModule{

	public EXPurchasesModule() {
		super("EXPurchasesModule", "Purchases module", "", new String[]{}, "12");
		
		GridFrame frame = new GridFrame(PurchasesOrder.class);
		addChild(frame, "0:0");
		
	}

}
