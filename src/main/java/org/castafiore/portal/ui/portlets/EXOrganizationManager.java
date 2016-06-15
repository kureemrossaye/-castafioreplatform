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
package org.castafiore.portal.ui.portlets;

import org.castafiore.security.SecurityService;
import org.castafiore.security.ui.CommunityTabModel;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("organizationManager")
@Scope("prototype")
public class EXOrganizationManager extends EXContainer {


	
	
	@Autowired
	public EXOrganizationManager(SecurityService service, MessageSource messageSource) {
		super("EXOrganizationManager", "div");
		EXTabPanel tabs = new EXTabPanel("mainTab", new CommunityTabModel(service, messageSource));
		addChild(tabs);

	}

}
