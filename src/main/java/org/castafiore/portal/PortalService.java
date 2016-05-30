package org.castafiore.portal;

import org.castafiore.portal.annotations.UIBuilder;
import org.castafiore.portal.model.Portal;
import org.castafiore.ui.Container;


public interface PortalService {
	
	public Portal getAdminPortal();
	
	
	public Container getContainer(String name);
	
	public UIBuilder getUIBuilder();

}
