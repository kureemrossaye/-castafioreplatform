package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Item;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXItemsModule extends AbstractEXModule{

	public EXItemsModule() {
		super("EXItemsModule", "Inventory", "icon-table", new String[]{"Referential"}, "12:12");
		
		GridFrame ref = new GridFrame(Item.class);
		
		addChild(ref, "0:0");
		
		
		
		
		
		
		
	}

}
