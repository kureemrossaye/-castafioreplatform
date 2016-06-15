package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Organization;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;

public class EXOrganizationModule extends AbstractEXModule{

	public EXOrganizationModule() {
		super("Organization", "Organization", "organization.png", new String[]{"organization"}, "12");
		
		
		GridFrame frame = new GridFrame(Organization.class);
		addChild(frame, "0:0");
	}
	
	

}
