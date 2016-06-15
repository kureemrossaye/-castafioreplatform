package org.castafiore.security.ui.membership;

import java.util.List;

import org.castafiore.portal.ui.data.DataGridController;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.data.SimpleDataLocator;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Role;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.springframework.context.MessageSource;

public class EXMemberShipsTab extends EXContainer implements DataGridController<Role> {

	private SecurityService service;

	public EXMemberShipsTab(String name, SecurityService service, MessageSource messageSource) {
		super(name, "div");
		this.service = service;
		SimpleDataLocator<Role> locator = new SimpleDataLocator<Role>(Role.class, messageSource) {

			@Override
			public List<Role> loadAll() {

				try {
					return service.getRoles();
				} catch (Exception e) {
					throw new UIException(e);
				}
			}

		};

		EXDataGrid<Role> grid = new EXDataGrid<>(Role.class, locator, this, new EXMembershipForm("", messageSource));
		addChild(grid);
	}

	@Override
	public void insertRecord(Role record) {

		try {
			service.saveOrUpdateRole(record.getName(), record.getDescription());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void deleteRecord(Role record) {

		try {
			service.deleteRole(record.getName());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void updateRecord(Role record) {
		insertRecord(record);
	}

}
