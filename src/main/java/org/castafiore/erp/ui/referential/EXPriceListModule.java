package org.castafiore.erp.ui.referential;

import org.castafiore.erp.PriceList;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXPriceListModule extends AbstractEXModule{

	public EXPriceListModule() {
		super("EXPriceListModule", "Sales price list", "icon-list-ol", new String[]{"Sales", "Price List"}, "12");
		
		
		GridFrame frame = new GridFrame(PriceList.class);
		
		addChild(frame,"0:0");
		
	}

}
