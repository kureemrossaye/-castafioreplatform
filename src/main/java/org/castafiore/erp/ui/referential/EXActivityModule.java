package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.module.grids.EXActivity;

public class EXActivityModule extends AbstractEXModule{

	public EXActivityModule() {
		super("EXActivityModule", "Activity", "icon-wrench", new String[]{"Activity", "Activity"}, "12");
		
		EXActivity activity = new EXActivity();
		
		addChild(activity, "0:0");
	}

}
