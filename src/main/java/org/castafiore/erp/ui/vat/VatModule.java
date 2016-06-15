package org.castafiore.erp.ui.vat;

import org.castafiore.erp.Vat;
import org.castafiore.erp.VatMatrix;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.configs.ConfigAccordion;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class VatModule extends AbstractEXModule{

	public VatModule() {
		super("Vat", "Vat");
		
		ConfigAccordion acc = new ConfigAccordion("acc");
		GridFrame frame = new GridFrame(Vat.class);
		acc.addItem("Vat codes", "", frame);
		//addChild(frame, "0:0");
		
		GridFrame vatMatrix = new GridFrame(VatMatrix.class);
		//addChild(vatMatrix,"0:0");
		acc.addItem("Vat code matrix", "", vatMatrix);
		
		addChild(acc,"0:0");
	}

}
