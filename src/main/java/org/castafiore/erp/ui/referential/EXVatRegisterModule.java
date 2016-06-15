package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Vat;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXVatRegisterModule extends AbstractEXModule{

	public EXVatRegisterModule() {
		super("EXVatRegisterModule", "Vat registers", "icon-eye-open", new String[]{"Accountings", "Vat registers"}, "12");
		GridFrame vat = new GridFrame(Vat.class);
		addChild(vat, "0:0");
		
		
	}

}
