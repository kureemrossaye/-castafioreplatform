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

import org.castafiore.portal.ui.widgets.EXWidget;
import org.castafiore.portal.ui.widgets.EXWizardWidget;
import org.castafiore.portal.ui.widgets.EXWizardWidget.WizardEventHandler;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.security.ui.users.permissions.EXPermissionTab;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class EXUserForm extends EXWizardWidget implements WizardEventHandler {

	private User user_;

	private EXAccountSettingForm accountSetting;

	private EXProfileForm profile;

	private EXPermissionTab permission;

	private SecurityService service;

	private EXTable table_;

	private boolean isNew = false;

	public EXUserForm(User user, SecurityService service, EXTable table) {
		super("EXUserForm", "Create / Edit User");
		this.user_ = user;
		this.table_ = table;
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

	public void setUser(User u) {
		this.user_ = u;
		if (StringUtil.isNotEmpty(u.getUsername())) {
			isNew = false;
		} else {
			isNew = true;
		}
		accountSetting.setUser(user_);
		profile.setUser(user_);
		permission.setUser(user_);
	}

	@Override
	public void onLeaveStep(Integer step, EXWidget widget) {
		try {
			if (step == 0) {
				this.user_ = accountSetting.save();
			} else if (step == 1) {

				this.user_ = profile.save();
				if (isNew) {
					service.saveOrUpdateUser(user_);
					isNew = false;
				} else {
					service.saveOrUpdateUser(user_);
				}
				table_.setModel(table_.getModel());
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
			accountSetting.setUser(user_);
		} else if (step == 1) {
			profile.setUser(user_);
		} else {
			permission.setUser(user_);
		}
	}

	@Override
	public void onFinish() {
		this.setDisplay(false);
		table_.setDisplay(true);

	}

}
