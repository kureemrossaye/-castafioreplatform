/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.security;

import java.util.List;

import org.castafiore.security.model.Group;
import org.castafiore.security.model.Role;
import org.castafiore.security.model.User;
import org.castafiore.security.model.UserSecurity;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public interface SecurityService {

	public User loadUserByEmail(String phone);

	public User loadUserByMobilePhone(String phone);
	

	/**
	 * returns the user with the user name
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public User loadUserByUsername(String username) ;

	public void unAssignSecurity(String user, String role, String group);

	/**
	 * logs the user
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean login(String username, String password) throws Exception;

	public void logout(String sessionid);

	public Role getRole(String name) throws Exception;

	public Group getGroup(String name) throws Exception;

	/**
	 * return roles for the user in the specified group
	 * 
	 * @param username
	 * @param group
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRolesInGroup(String username, String group) throws Exception;

	/**
	 * returns all the groups that the user belongs to
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<Group> getGroups(String username) throws Exception;

	/**
	 * return all groups that the user belong to with a particular role
	 * 
	 * @param username
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<Group> getGroups(String username, String role) throws Exception;

	/**
	 * returns all roles configured on the platform
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Role> getRoles() throws Exception;

	/**
	 * returns all groups configured on the platform
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Group> getGroups() throws Exception;

	/**
	 * saves or updates the specified group
	 * 
	 * @param group
	 * @throws Exception
	 */
	public Group saveOrUpdateGroup(String name, String description) throws Exception;

	/**
	 * saves the specified role in the repository
	 * 
	 * @param role
	 * @throws Exception
	 */
	public Role saveOrUpdateRole(String name, String description) throws Exception;

	/**
	 * assign a role of a group to a user
	 * 
	 * @param user
	 * @param role
	 * @param group
	 * @throws Exception
	 */
	public void assignSecurity(User user, Role role, Group group) throws Exception;

	/**
	 * 
	 * @param user
	 * @param role
	 * @param group
	 * @throws Exception
	 */
	public void assignSecurity(String user, String role, String group) throws Exception;

	/**
	 * 
	 * @param user
	 * @param role
	 * @param group
	 * @throws Exception
	 */
	public void assignSecurity(String user, String role, String group, boolean forceCreateRoleAndGroup)
			throws Exception;

	/**
	 * verifies if user is allowed according to the specification
	 * 
	 * 
	 * @param accessPermission
	 *            "<role>:<group>" role can be "*" to indicate any
	 * @param username
	 *            - the username of the user
	 * @return
	 * @throws Exception
	 */
	public boolean isUserAllowed(String accessPermission, String username) throws Exception;

	/**
	 * verifies if user is allowed according to the specifications
	 * 
	 * 
	 * @param accessPermission
	 *            "<role>:<group>" role can be "*" to indicate any
	 * @param username
	 *            - the username of the user
	 * @return
	 * @throws E
	 */
	public boolean isUserAllowed(String[] accessPermissions, String username) throws Exception;

	/**
	 * returns permissions in the form <role>:<group> for this particular user
	 * can return a "*" which means full access to all resources
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */

	public List<UserSecurity> getPermissionSpec(String username) throws Exception;

	/**
	 * Register a user into the system
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void registerUser(User user) throws Exception;

	/**
	 * saves or updates a user
	 * 
	 * @param user
	 */
	public void saveOrUpdateUser(User user);

	/**
	 * delete a user with the specified id
	 * 
	 * @param id
	 */
	public void deleteUser(String name) throws Exception;

	/**
	 * deletes a group with the specified id
	 * 
	 * @param id
	 */
	public void deleteGroup(String name) throws Exception;

	/**
	 * deletes a role with the specified id
	 * 
	 * @param id
	 */
	public void deleteRole(String name) throws Exception;

	public List<User> getAllUsers();

}
