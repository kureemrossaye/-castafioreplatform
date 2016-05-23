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
package org.castafiore.community.ui.users;

import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Address;
import org.castafiore.security.model.User;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.utils.StringUtil;

public class EXProfileForm extends EXDynaformPanel {

	private SecurityService service;

	public EXProfileForm(User user, SecurityService service) {
		super("EXProfileForm", "Address");
		this.service = service;
		addField("Address Line 1 :", new EXInput("line1"));
		addField("Address Line 2 :", new EXInput("line2"));
		addField("City :", new EXInput("city"));
		addField("Postal Code :", new EXInput("postalCode"));
		addField("Country :", new EXInput("country"));

		EXButton saveButton = new EXButton("Save", "Save");
		saveButton.setAttribute("method", "save");
		saveButton.addEvent(CommunityEvents.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		saveButton.setType(EXButton.TYPE_DANGER);
		addButton(saveButton);
		addButton(new EXButton("Cancel", "Cancel").setType(EXButton.TYPE_LINK));
		setShowCloseButton(false);
		setShowHeader(false);

		setDraggable(false);
		setWidth(Dimension.parse("100%"));
		setUser(user);
	}

	@SuppressWarnings("unchecked")
	protected void setUser(User user) {
		if (user.getUsername() != null) {
			setAttribute("username", user.getUsername());
			// user = service.hydrate(user);

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

	public void save() throws Exception {
		String username = getAttribute("username");

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
			((UserProfileTabModel) getAncestorOfType(EXTabPanel.class).getModel()).setUser(u);
		} else {
			throw new UIException("Please save the user first before saving its address");
		}

	}

}
