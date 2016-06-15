package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Warehouse;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXWarehouseModule extends AbstractEXModule{

	public EXWarehouseModule() {
		super("EXWarehouseModule", "Warehouse", "icon-truck", new String[]{"WareHouse"}, "12;12");
	
		GridFrame ref = new GridFrame(Warehouse.class);
		
		
		
		addChild(ref, "0:0");
		
	}

}
