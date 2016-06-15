package org.castafiore.erp.ui.security;

import org.castafiore.community.ui.EXCommunity;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXCommunityModule extends AbstractEXModule{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXCommunityModule() {
		super("EXCommunityModule", "Organization", "icon-user", new String[]{"organization"}, "12");
		
		EXPanel panel = new EXPanel("Panel", "Organization management");
		
		EXCommunity community = new EXCommunity();
		
		panel.setBody(community);
		
		addChild(panel, "0:0");
		
		
	}

}
