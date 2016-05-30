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
 package org.castafiore.security.ui;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.security.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.panel.EXPanel;

public interface CommunityEvents {
	
	public final static Event DELETE_USER_EVENT = new Event(){

		public void ClientAction(JQuery container) {
			container.makeServerRequest(this, "Do you really want to delete this user?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String username = container.getAttribute("username");
			try{
				SecurityService service =  container.getRoot().getService(SecurityService.class);
				service.deleteUser(username);
				container.getAncestorOfType(EXUsersTab.class).ServerAction(new EXContainer("refresh", "div"), new HashMap<String,String>());
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}

		public void Success(JQuery container, Map<String, String> request)
				throws UIException {
		}
		
	};
	
	
	
	
	
	
		
		
	
	
	
	
	

}
