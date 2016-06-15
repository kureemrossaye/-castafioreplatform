package org.castafiore.security.ui.groups;

import java.util.List;

import org.castafiore.portal.ui.data.DataGridController;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.data.SimpleDataLocator;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Group;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.springframework.context.MessageSource;

public class EXGroupsTab extends EXContainer implements DataGridController<Group> {

	private SecurityService service;

	public EXGroupsTab(String name, SecurityService service, MessageSource messageSource) {
		super(name, "div");
		try {
			this.service = service;

			SimpleDataLocator<Group> locator = new SimpleDataLocator<Group>(Group.class, messageSource) {

				@Override
				public List<Group> loadAll() {

					try {
						return service.getGroups();
					} catch (Exception e) {
						throw new UIException(e);
					}
				}

			};

			EXDataGrid<Group> grid = new EXDataGrid<>(Group.class, locator, this, new EXGroupsForm(service));
			addChild(grid);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insertRecord(Group record) {
		try {
			service.saveOrUpdateGroup(record.getName(), record.getDescription());
		} catch (Exception e) {
			throw new UIException(e);
		}

	}

	@Override
	public void deleteRecord(Group record) {
		try {
			service.deleteGroup(record.getName());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void updateRecord(Group record) {
		insertRecord(record);
	}

}
