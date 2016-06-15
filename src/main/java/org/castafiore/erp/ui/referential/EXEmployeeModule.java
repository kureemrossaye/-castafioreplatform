package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Employee;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXEmployeeModule extends AbstractEXModule{

	public EXEmployeeModule() {
		super("EXEmployeeModule", "Employees", "", new String[]{"Administrations", "Employees"}, "12");
		
		GridFrame frame = new GridFrame(Employee.class);
		addChild(frame, "0:0");
		
	}
	
	

}
