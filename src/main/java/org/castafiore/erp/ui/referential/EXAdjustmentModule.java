package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Adjustment;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXAdjustmentModule extends AbstractEXModule{

	public EXAdjustmentModule() {
		super("EXAdjustmentModule", "Adjustments", "icon-exchange", new String[]{"Inventory", "Adjustment"}, "12");
		
		GridFrame adjustment = new GridFrame(Adjustment.class);
		
		
		addChild(adjustment, "0:0");
		
		
		
		
	}

}
