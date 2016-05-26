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
 package org.castafiore.community.ui;

import org.castafiore.portal.EXPortal;
import org.castafiore.security.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.tabbedpane.EXTabPanel;

public class EXCommunity extends EXContainer implements PopupContainer {

	
	
	private EXPortal portal = new EXPortal("portal");
	
	
	public EXCommunity(SecurityService service) {
		super("EXCommunity", "div");
		
		addChild(portal);
		addChild(new EXContainer("clear", "div").addClass("clearfix"));
		
		EXTabPanel tabs = new EXTabPanel("mainTab",new CommunityTabModel(service));
		tabs.setWidth(Dimension.parse("100%"));
		portal.setBody("User management", "Portlet to manage organization", tabs);
		//addChild(tabs);
		//addChild(new EXOverlayPopupPlaceHolder("overlay"));
		
	}

	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		
	}

}
