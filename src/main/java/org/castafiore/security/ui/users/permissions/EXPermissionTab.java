package org.castafiore.security.ui.users.permissions;

import org.castafiore.portal.ui.widgets.EXWidget;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;

public class EXPermissionTab extends EXWidget {

	private EXPermissionsForm form = null;

	private EXPermissionsTable table_;

	
	private SecurityService service_;
	public EXPermissionTab(SecurityService service) {
		super("EXPermissionTab", "Configure Permissions");
		this.service_ = service;
	}

	public void setUser(User user) {
		this.getChildren().clear();
		setRendered(false);
		table_ = new EXPermissionsTable("pTable", user.getUsername(), service_);
		addChild(table_);
		form = new EXPermissionsForm(user, service_, table_);
		addChild(form);
		
		
	}

}
