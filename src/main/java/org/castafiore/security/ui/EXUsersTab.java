package org.castafiore.security.ui;

import org.castafiore.portal.ui.data.DataGridController;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.data.UserDataLocator;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.security.ui.users.EXUserForm;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.springframework.context.MessageSource;

public class EXUsersTab extends EXContainer implements DataGridController<User> {
	
	private SecurityService service;
	
	//private MessageSource messageSource_;

	public EXUsersTab(String name, SecurityService service, MessageSource messageSource) {
		super(name, "div");
		this.service = service;
		//this.messageSource_ = messageSource;
		try {
			
			EXUserForm form = new EXUserForm(new User(), service);
			EXDataGrid<User> grid  = new EXDataGrid<User>(User.class, new UserDataLocator(messageSource,service), this, form);
			addChild(grid);
			
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void insertRecord(User record) {
		service.saveOrUpdateUser(record);
	}

	@Override
	public void deleteRecord(User record) {
		try {
			service.deleteUser(record.getUsername());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void updateRecord(User record) {
		service.saveOrUpdateUser(record);
	}

}
