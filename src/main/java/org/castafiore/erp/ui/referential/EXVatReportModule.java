package org.castafiore.erp.ui.referential;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.vat.VatTable;

public class EXVatReportModule extends AbstractEXModule {

	public EXVatReportModule() {
		super("EXVatReportModule", "Vat report", "", new String[]{}, "12");
		
		
		VatTable vat = new VatTable("vattable");
		
		addChild(vat, "0:0");
	}

}
