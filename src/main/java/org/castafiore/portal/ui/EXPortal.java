package org.castafiore.portal.ui;

import org.castafiore.portal.PortalService;
import org.castafiore.portal.model.NavigationNode;
import org.castafiore.portal.model.Page;
import org.castafiore.portal.model.Portal;
import org.castafiore.portal.model.Portlet;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("portal")
@Scope("session")
public class EXPortal extends EXApplication {

	private EXLeftPanel leftPanel = new EXLeftPanel("leftPanel");

	private EXRightPanel rightPanel = new EXRightPanel("rightPanel");

	private Portal portal;

	@Autowired
	private PortalService portalService;

	@Autowired
	public EXPortal(PortalService portalService) {
		super("portal");
		this.portal = portalService.getAdminPortal();
		this.portalService = portalService;
		addClass("mainwrapper").addClass("fullwrapper");
		addChild(leftPanel);
		addChild(rightPanel);
		leftPanel.setPortal(portal);

	}

	public Container getLeftPanel() {
		return leftPanel;
	}

	public Container getRightPanel() {
		return rightPanel;
	}

	public EXPortal setBody(String title, String desc, Container body) {

		rightPanel.setBody(title, desc, body);
		return this;
	}

	public PortalService getPortalService() {
		return portalService;
	}

	public void setPortalService(PortalService portalService) {
		this.portalService = portalService;
	}

	public Portal getPortal() {
		return portal;
	}
	
	public EXPortal redirectTo(NavigationNode node){
		Page page = node.getPage();
		Portlet portlet = page.getPortlets().get(0);
		Container body = getService(portlet.getImplementation(), Container.class);
		rightPanel.setBody(page.getLabel(), page.getDescription(), body);
		return this;
	}

}
