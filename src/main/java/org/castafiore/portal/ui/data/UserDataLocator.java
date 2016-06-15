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
package org.castafiore.portal.ui.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.castafiore.security.SecurityService;
import org.castafiore.security.model.User;
import org.springframework.context.MessageSource;

public class UserDataLocator extends SimpleDataLocator<User> {

	private SecurityService service;

	public UserDataLocator( MessageSource messageSource, SecurityService service) {
		super(User.class, messageSource);
		this.service = service;
	}
	
	public List<User> loadAll(){
		 List<User> users = new ArrayList<User>();
		service.getAllUsers().forEach(new Consumer<User>() {
			@Override
			public void accept(User t) {
				users.add(t);
			}
		});
		
		return users;
	}

	

	

}
