package org.castafiore.community.ui.users;

import java.util.Map;

import org.castafiore.security.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXPermissionTab extends EXContainer implements Event{

	
	private SecurityService service;
	public EXPermissionTab(String name, String username, SecurityService service) {
		super(name, "div");
		this.service = service;
		addChild(new EXPermissionsTable("pTable", username, service).setStyle("clear", "left").setStyle("padding", "10px 0px"));
		setAttribute("username", username);
		
		EXPermissionsForm form = new EXPermissionsForm("", getAttribute("username"),service);
		form.setDraggable(false);
		form.setShowCloseButton(false);
		addChild(form);
	}
	
	public void setUser(String username){
		getDescendentOfType(EXPermissionsTable.class).setUser(username);
		setAttribute("username", username);
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(PopupContainer.class).addPopup(new EXPermissionsForm("", getAttribute("username"), service).setStyle("z-index", "5001"));
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
