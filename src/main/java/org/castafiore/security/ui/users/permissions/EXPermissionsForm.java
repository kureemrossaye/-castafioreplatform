package org.castafiore.security.ui.users.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.castafiore.portal.ui.widgets.EXFormWidget;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Group;
import org.castafiore.security.model.Role;
import org.castafiore.security.model.User;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class EXPermissionsForm extends EXFormWidget implements Event {

	private SecurityService service;

	private EXPermissionsTable table_;

	private User user_;

	private EXSelect<Role> roles_ = null;

	private EXSelect<Group> groups_ = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EXPermissionsForm( User user,  SecurityService service,EXPermissionsTable table) {
		super(user.getUsername(), "Add Permission");
		try {
			this.service = service;
			this.user_ = user;
			this.table_ = table;
			final List roles = new ArrayList<Role>();
			service.getRoles().forEach(new Consumer<Role>() {

				@Override
				public void accept(Role t) {
					roles.add(t);
				}
			});
			this.roles_ = new EXSelect<Role>("role", new DefaultDataModel<Role>(roles));
			addField("Role :", this.roles_);

			List groups = new ArrayList<Group>();
			service.getGroups().forEach(new Consumer<Group>() {

				@Override
				public void accept(Group t) {
					groups.add(t);
				}

			});

			addField("Groups :", groups_ = new EXSelect<Group>("group", new DefaultDataModel<Group>(groups)));

			addButton(new EXButton("save", "Add"));
			getDescendentByName("save").addEvent(this, CLICK);
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);

	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		try {
			Group grp = groups_.getValue();
			Role role = roles_.getValue();
			String username = user_.getUsername();
			if (StringUtil.isNotEmpty(username)) {
				service.assignSecurity(username, role.getName(), grp.getName());
				table_.setUser(username);
			} else {
				throw new UIException("Please save the user befor assigning permission");
			}
		} catch (Exception e) {
			throw new UIException(e);
		}

		return true;

	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {

	}

}
