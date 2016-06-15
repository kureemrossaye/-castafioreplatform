package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Ledger;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXLedgerModule extends AbstractEXModule{

	public EXLedgerModule() {
		super("EXLedgerModule", "Ledger", "icon-dollar", new String[]{"Accounting", "Ledger"}, "12");
		
		GridFrame frame = new GridFrame(Ledger.class);
		
		
		
		addChild(frame,"0:0");
		
	}

}
