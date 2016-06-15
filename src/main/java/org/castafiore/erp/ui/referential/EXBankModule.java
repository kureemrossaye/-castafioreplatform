package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Adjustment;
import org.castafiore.erp.Bank;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXBankModule extends AbstractEXModule {

	public EXBankModule() {
		super("EXBankModule", "EXBankModule", "icon-exchange", new String[] {
				"Inventory", "Adjustment" }, "12");

		GridFrame banks = new GridFrame(Bank.class);

		addChild(banks, "0:0");

	}

}