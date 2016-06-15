/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.castafiore.security.ui.users;

import org.castafiore.portal.ui.data.DataForm;
import org.castafiore.portal.ui.data.EXDataGrid;
import org.castafiore.portal.ui.widgets.EXWidget;
import org.castafiore.portal.ui.widgets.EXWizardWidget;
import org.castafiore.portal.ui.widgets.EXWizardWidget.WizardEventHandler;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.security.ui.users.permissions.EXPermissionTab;
import org.castafiore.ui.UIException;
import org.castafiore.utils.StringUtil;

public class EXUserForm extends EXWizardWidget implements WizardEventHandler, DataForm<User> {

	private User user_;

	private EXAccountSettingForm accountSetting;

	private EXProfileForm profile;

	private EXPermissionTab permission;

	private SecurityService service;

	private EXDataGrid<User> table_;

	private boolean isNew = false;

	public EXUserForm(User user, SecurityService service) {
		super("EXUserForm", "Create / Edit User");
		this.user_ = user;
		//this.table_ = table;
		this.service = service;
		accountSetting = new EXAccountSettingForm(service);
		profile = new EXProfileForm(service);
		permission = new EXPermissionTab(service);

		addStep("Account Setting", accountSetting);
		addStep("User Profile", profile);
		addStep("Permission Settings", permission);
		addEventHandler(this);
		move(1);

	}


	@Override
	public void onLeaveStep(Integer step, EXWidget widget) {
		try {
			if (step == 0) {
				this.user_ = accountSetting.getModel();
			} else if (step == 1) {

				this.user_ = profile.getModel();
				if (isNew) {
					service.saveOrUpdateUser(user_);
					isNew = false;
				} else {
					service.saveOrUpdateUser(user_);
				}
				table_.getTable().setModel(table_.getTable().getModel());
			} else {
				// this.user_ =
			}
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	@Override
	public void onShowStep(Integer step, EXWidget widget) {
		if (step == 0) {
			accountSetting.setModel(user_);
		} else if (step == 1) {
			profile.setModel(user_);
		} else {
			permission.setModel(user_);
		}
	}

	@Override
	public void onFinish() {
		table_.finish();
	}

	@Override
	public void setModel(User u) {
		this.user_ = u;
		if (StringUtil.isNotEmpty(u.getUsername())) {
			isNew = false;
		} else {
			isNew = true;
		}
		accountSetting.setModel(user_);
		profile.setModel(user_);
		permission.setModel(user_);
	}

	@Override
	public User getModel() {
		return user_;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public void cancel() {
		table_.cancel();
	}


	@Override
	public void setDataGrid(EXDataGrid<User> grid) {
		this.table_ = grid;
		
	}

}
