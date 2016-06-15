package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Customer;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXCustomerModule extends AbstractEXModule{

	public EXCustomerModule() {
		super("EXCustomerModule", "Customer", "icon-briefcase", new String[]{"Customers"}, "12");
		 
		GridFrame frame = new GridFrame(Customer.class);
		addChild(frame, "0:0");
	
		
	}

}
