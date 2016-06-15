package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.ui.ex.EXContainer;

@SuppressWarnings("serial")
public class EXUnderConstructionModule extends AbstractEXModule{

	public EXUnderConstructionModule() {
		super("EXUnderConstructionModule", "Under construction", "icon-code", new String[]{"Under construction"}, "12");
		
		addChild(new EXContainer("", "h1").setText("Under contruction"), "0:0");
	}

}
