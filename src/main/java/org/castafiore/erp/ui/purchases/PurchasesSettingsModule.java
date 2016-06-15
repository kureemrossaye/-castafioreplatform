package org.castafiore.erp.ui.purchases;

import org.castafiore.erp.SupplierCategory;
import org.castafiore.erp.SupplierType;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.configs.ConfigAccordion;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class PurchasesSettingsModule extends AbstractEXModule{

	public PurchasesSettingsModule() {
		super("SalesSettings", "Sales Settings");
		
		//currency
		//Customer category
		//Customer type
		//Business Units
		
		GridFrame category = new GridFrame(SupplierCategory.class);
		GridFrame type = new GridFrame(SupplierType.class);
		
		ConfigAccordion acc = new ConfigAccordion("as");
		acc.addItem("Supplier category", "", category);
		acc.addItem("Supplier type", "", type);
		
		
		addChild(acc, "0:0");
		
	}

}
