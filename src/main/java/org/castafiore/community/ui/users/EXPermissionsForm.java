package org.castafiore.community.ui.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Group;
import org.castafiore.security.model.Role;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class EXPermissionsForm extends EXDynaformPanel implements Event {

	private SecurityService service;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EXPermissionsForm(String name, String username, SecurityService service) {
		super(name, "Add Permission");
		try {
			this.service = service;
			setAttribute("username", username);
			final List roles = new ArrayList<Role>();
			service.getRoles().forEach(new Consumer<Role>() {

				@Override
				public void accept(Role t) {
					roles.add(t);
				}
			});
			addField("Role :", new EXSelect("role", new DefaultDataModel<Object>(roles)));

			List groups = new ArrayList<Group>();
			service.getGroups().forEach(new Consumer<Group>() {

				@Override
				public void accept(Group t) {
					groups.add(t);
				}

			});

			addField("Groups :", new EXSelect("group", new DefaultDataModel<Object>(groups)));

			addButton(new EXButton("save", "Save"));
			addButton(new EXButton("cancel", "Cancel"));
			getDescendentByName("save").addEvent(this, CLICK);
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		try {
			Group grp = (Group) ((EXSelect) getDescendentByName("group")).getValue();
			Role role = (Role) ((EXSelect) getDescendentByName("role")).getValue();
			if (StringUtil.isNotEmpty(getAttribute("username"))) {
				String username = getAttribute("username");
				service.assignSecurity(username, role.getName(), grp.getName());
				container.getAncestorOfType(EXPermissionTab.class).getDescendentOfType(EXPermissionsTable.class)
						.setUser(username);
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
