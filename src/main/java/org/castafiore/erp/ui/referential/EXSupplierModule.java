package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Supplier;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXSupplierModule extends AbstractEXModule{

	public EXSupplierModule() {
		super("EXSupplierModule", "Vendors", "icon", new String[]{}, "12");
		
		GridFrame frame = new GridFrame(Supplier.class);
		
		addChild(frame, "0:0");
	}

}
