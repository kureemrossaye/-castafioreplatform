package org.castafiore.security.ui.users.permissions;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.security.SecurityService;
import org.castafiore.security.model.UserSecurity;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class EXPermissionsTable extends EXTable implements TableModel, CellRenderer, Event {

	private List<UserSecurity> specs;

	private SecurityService service;

	public EXPermissionsTable(String name, String username, SecurityService service) {
		super(name, null);
		this.service = service;
		setUser(username);
		setCellRenderer(this);
	}

	public void setUser(String username) {
		if (username == null) {
			setAttribute("username", "");
			specs = new LinkedList<UserSecurity>();
			setModel(this);
		} else {
			setAttribute("username", username);
			try {
				specs = service.getPermissionSpec(username);
			} catch (Exception e) {
				throw new UIException(e);
			}
			setModel(this);
		}

	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnNameAt(int index) {
		if (index == 0)
			return "#";
		else if (index == 1)
			return "Permission";
		else
			return "";

	}

	@Override
	public int getRowCount() {
		return specs.size();
	}

	@Override
	public int getRowsPerPage() {
		return getRowCount();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return specs.get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Container getComponentAt(int row, int column, int page, TableModel model, EXTable table) {
		if (column == 0) {
			return new EXContainer("", "label").setText(row + "");
		} else if (column == 1) {
			String s = specs.get(row).toString();
			return new EXContainer("ss", "a").setAttribute("href", "#").setText(s);
		} else {
			return new EXIconButton("delete", "icon-delete").addEvent(this, CLICK);
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column, int page, TableModel model, EXTable table) {
		if (row == 1) {
			String s = specs.get(row).toString();
			component.setText(s);
		}

	}

	@Override
	public void ClientAction(JQuery container) {
		container.makeServerRequest(this, "Do you really want to delete this permission?");

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		// SecurityService s = SpringUtil.getSecurityService();
		String spec = container.getParent().getParent().getDescendentByName("ss").getText();
		String[] parts = StringUtil.split(spec, ":");
		service.unAssignSecurity(getAttribute("username"), parts[0], parts[1]);
		setUser(getAttribute("username"));
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {

	}

}
