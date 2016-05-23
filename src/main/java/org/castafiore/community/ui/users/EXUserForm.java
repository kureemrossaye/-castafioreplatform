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

import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.castafiore.ui.ex.panel.EXModal;
import org.castafiore.ui.tabbedpane.EXTabPanel;

public class EXUserForm extends EXModal{

	public EXUserForm(User user, SecurityService service) {
		super("EXUserForm", "User");
		setBody(new EXTabPanel("userss", new UserProfileTabModel(user, service)));
		setStyle("width", "100%");
		getDialog().setStyle("width", "1200px");
		//setWidth(Dimension.parse("800px"));
	}
	


}
