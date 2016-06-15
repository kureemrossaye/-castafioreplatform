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
import org.castafiore.security.model.Address;
import org.castafiore.security.model.User;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;

public class EXProfileForm extends EXDataForm<User> {

	private SecurityService service;

	private User user_;

	public EXProfileForm(SecurityService service) {
		super("EXProfileForm", "Address");
		this.service = service;
		addField("Address Line 1 :", new EXInput("line1"));
		addField("Address Line 2 :", new EXInput("line2"));
		addField("City :", new EXInput("city"));
		addField("Postal Code :", new EXInput("postalCode"));
		addField("Country :", new EXInput("country"));

	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModel(User user) {
		this.user_ = user;
		if (user.getUsername() != null) {
			Address a = user.getDefaultAddress();
			if (a != null) {
				getField("line1").setValue(a.getLine1());
				getField("line2").setValue(a.getLine1());
				getField("city").setValue(a.getCity());
				getField("postalCode").setValue(a.getPostalCode());
				getField("country").setValue(a.getCountry());
			}

		}

	}

	@Override
	public User getModel() {
		String username = user_.getUsername();

		if (StringUtil.isNotEmpty(username)) {
			User u = service.loadUserByUsername(username);
			Address a = u.getDefaultAddress();
			if (a == null) {
				a = new Address();
				u.addAddress(a);
			}
			a.setCity(getField("city").getValue().toString());
			a.setLine1(getField("line1").getValue().toString());
			a.setLine2(getField("line2").getValue().toString());
			a.setCountry(getField("country").getValue().toString());
			a.setPostalCode(getField("postalCode").getValue().toString());
			a.setDefaultAddress(true);
			service.saveOrUpdateUser(u);
			return u;
		} else {
			throw new UIException("Please save the user first before saving its address");
		}
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		User u  = new User();
		u.addAddress(new Address());
		setModel(u);
	}

}
