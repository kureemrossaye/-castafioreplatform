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

import org.castafiore.portal.ui.data.EXDataForm;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXAccountSettingForm extends EXDataForm<User> {

	private SecurityService service;

	private boolean isNew = false;

	public EXAccountSettingForm(SecurityService service) {
		super("EXAccountSettingForm", "Account setting");
		this.service = service;
		addField("Email address :", new EXInput("email"));
		addField("Password :", new EXPassword("password"));
		addField("Confirm password :", new EXPassword("confirmPassword"));
		addField("First name :", new EXInput("firstName"));
		addField("Last name :", new EXInput("lastName"));
		addField("Phone :", new EXInput("phone"));
		addField("Mobile :", new EXInput("mobile"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(User user) {
		getField("email").setValue(user.getUsername());
		getField("password").setValue(user.getPassword());
		getField("firstName").setValue(user.getFirstName());
		getField("lastName").setValue(user.getLastName());
		getField("email").setValue(user.getEmail());
		getField("phone").setValue(user.getPhone());
		getField("mobile").setValue(user.getMobile());
		if (StringUtil.isNotEmpty(user.getUsername())) {
			((EXInput) getField("email")).setEnabled(false);
			isNew = false;
		} else {
			isNew = true;
		}
	}

	@Override
	public User getModel() {
		User u = null;
		String username = getField("email").getValue().toString();
		if (!isNew) {
			u = service.loadUserByUsername(username);
		} else {
			u = new User();
			u.setUsername(username);
		}
		u.setPassword(getField("password").getValue().toString());
		u.setAccountNonExpired(true);
		u.setAccountNonLocked(true);
		u.setCredentialsNonExpired(true);
		u.setEmail(getField("email").getValue().toString());
		u.setEnabled(true);
		u.setFirstName(getField("firstName").getValue().toString());
		u.setLastName(getField("lastName").getValue().toString());
		u.setPhone(getField("phone").getValue().toString());
		u.setMobile(getField("mobile").getValue().toString());
		u.setOrganization(Util.getLoggedOrganization());
		u.setMerchant(true);
		if (!isNew) {
			service.saveOrUpdateUser(u);
		} else {
			try {
				service.registerUser(u);
				isNew = false;
			} catch (Exception e) {
				throw new UIException(e);
			}
		}

		return u;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		setModel(new User());
	}

}
