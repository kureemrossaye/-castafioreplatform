package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.module.grids.EXCharge;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXChargesModule extends AbstractEXModule {

	public EXChargesModule() {
		super("EXChargesModule", "Charges", "icon-credit-card", new String[]{"Sales", "Charges"}, "12");
		
		EXPanel panel = new EXPanel("panel", "Charges");
		EXCharge charges = new EXCharge();
		panel.setBody(charges);
		addChild(panel, "0:0");
	}

}
