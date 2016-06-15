package org.castafiore.erp.ui.referential;

import org.castafiore.erp.PriceList;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXTransactionSettings extends AbstractEXModule{

	public EXTransactionSettings() {
		super("Settings", "Settings", "settings.png", new String[]{"Transactions", "Settings"}, "12");
		
		
		GridFrame priceList = new GridFrame(PriceList.class);
		addChild(priceList, "0:0");
	}

}
