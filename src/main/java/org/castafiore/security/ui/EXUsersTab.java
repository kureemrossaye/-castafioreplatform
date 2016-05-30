package org.castafiore.security.ui;

import java.util.Map;

import org.castafiore.portal.ui.widgets.grid.EXDataGrid;
import org.castafiore.portal.ui.widgets.grid.EXGridToolbar;
import org.castafiore.portal.ui.widgets.grid.GridController;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.security.ui.users.EXUserForm;
import org.castafiore.security.ui.users.UsersCellRenderer;
import org.castafiore.security.ui.users.UsersColumnModel;
import org.castafiore.security.ui.users.UsersTableModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXUsersTab extends EXContainer implements Event, GridController<User> {
	private SecurityService service;

	private EXUserForm form;

	private EXTable table;

	public EXUsersTab(String name, SecurityService service) {
		super(name, "div");
		this.service = service;
		
		EXDataGrid<User> grid = new EXDataGrid<User>("dataGrid");
		addChild(grid);
		
		try {
			table = new EXTable("usersList", new UsersTableModel(service));
			table.setCellRenderer(new UsersCellRenderer());
			table.setColumnModel(new UsersColumnModel());
			grid.setupTable(table, this);
			form = new EXUserForm(new User(),service, table);
			addChild(form);
			form.setDisplay(false);
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		if (container.getName().equalsIgnoreCase("refresh")) {
			try {
				table.setModel(new UsersTableModel(service));
				table.refresh();
			} catch (Exception e) {
				e.printStackTrace();
				throw new UIException(e);
			}
		} else {

			table.setDisplay(false);
			form.setDisplay(true);

		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {

	}

	@Override
	public void insertRecord(User record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRecord(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecord(User record) {
		// TODO Auto-generated method stub
		
	}

}
