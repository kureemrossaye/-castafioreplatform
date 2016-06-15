package org.castafiore.erp.ui.referential;

import org.castafiore.erp.BookEntry;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.module.grids.EXBookEntry;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXBookEntryModule extends AbstractEXModule{

	public EXBookEntryModule() {
		super("EXBookEntryModule", "Double entry", "icon-code-fork", new String[]{"Accountings", "Double entry"}, "12");
		
		GridFrame frame = new GridFrame(BookEntry.class);
		addChild(frame,"0:0");
		
		
	}

}
