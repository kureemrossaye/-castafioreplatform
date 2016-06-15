package org.castafiore.security.ui.users.permissions;

import org.castafiore.portal.ui.data.DataForm;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.widgets.EXWidget;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;

public class EXPermissionTab extends EXWidget implements DataForm<User> {

	private EXPermissionsForm form = null;

	private EXPermissionsTable table_;

	
	private SecurityService service_;
	public EXPermissionTab(SecurityService service) {
		super("EXPermissionTab", "Configure Permissions");
		this.service_ = service;
	}


	@Override
	public void setModel(User user) {
		this.getChildren().clear();
		setRendered(false);
		table_ = new EXPermissionsTable("pTable", user.getUsername(), service_);
		addChild(table_);
		form = new EXPermissionsForm(user, service_, table_);
		addChild(form);
		
	}

	@Override
	public User getModel() {
		return form.getModel();
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void reset() {
		
	}


	@Override
	public void setDataGrid(EXDataGrid<User> grid) {
		
	}

}
