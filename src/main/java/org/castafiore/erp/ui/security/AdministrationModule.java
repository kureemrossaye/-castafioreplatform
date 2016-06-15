package org.castafiore.erp.ui.security;

import org.castafiore.erp.ui.AbstractEXModule;

public class AdministrationModule extends AbstractEXModule{

	public AdministrationModule() {
		super("AdministrationModule", "Administrations", "", new String[]{""}, "0:0");
		SecurityMatrix matrix = new SecurityMatrix();
		addChild(matrix,"0:0");
	}

}
