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

package org.castafiore.security.neo4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Consumer;

import org.castafiore.security.Credential;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Group;
import org.castafiore.security.model.Role;
import org.castafiore.security.model.User;
import org.castafiore.security.model.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {

	private String superUser;

	private Map<String, User> cache_ = new WeakHashMap<String, User>();

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserSecurityRepository userSecurityRepository;

	@Autowired
	private Credential credential;

	public String getSuperUser() {
		return superUser;
	}

	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}

	public User loadUserByEmail(String email) {

		return userRepository.findOneByEmail(email);

	}

	public User loadUserByMobilePhone(String phone) {
		return userRepository.findOneByMobile(phone);
	}

	public List<Group> getGroups(String username) throws Exception {

		List<UserSecurity> uss = userSecurityRepository.findByUser_Username(username);
		List<Group> result = new LinkedList<Group>();
		if (uss != null) {
			for (UserSecurity u : uss) {
				result.add(u.getGrp());
			}
		}

		return result;
	}

	public boolean isUserAllowed(String username, String role, String group) {
		try{
		int count = userSecurityRepository.countByUser_UsernameAndRole_NameAndGrp_Name(username, role, group).size();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public List<Group> getGroups(String username, String role) throws Exception {
		List<Group> result = new ArrayList<Group>();

		List<UserSecurity> uss = userSecurityRepository.findByUser_UsernameAndRole_Name(username, role);
		if (uss != null) {
			for (UserSecurity u : uss) {

				result.add(u.getGrp());
			}
		}

		return result;
	}

	public List<Role> getRolesInGroup(String username, String group) throws Exception {
		List<Role> result = new ArrayList<Role>();

		List<UserSecurity> uss = userSecurityRepository.findByUser_UsernameAndGrp_Name(username, group);

		if (uss != null) {
			for (UserSecurity u : uss) {
				result.add(u.getRole());
			}
		}

		return result;
	}

	public User loadUserByUsername(String username) {

		return userRepository.findOneByUsername(username);

	}

	public boolean login(String username, String password) throws Exception {
		User user = loadUserByUsername(username);

		if (user != null && user.getPassword().equals(password)) {

			try {
				credential.setRemoteUser(username);
				credential.setOrganization(user.getOrganization());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public void logout(String sessionid) {

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void assignSecurity(User user, Role role, Group group) throws Exception {
		Assert.notNull(group, "assignSecurity: group is null");
		Assert.notNull(user, "assignSecurity: user is null");
		Assert.notNull(role, "assignSecurity: role is null");
		if (!isUserAllowed(user.getUsername(), role.getName(), group.getName())) {
			UserSecurity security = new UserSecurity();
			security.setUser(user);
			security.setRole(role);
			security.setGrp(group);
			userSecurityRepository.save(security);

		}
	}

	public Role getRole(String name) throws Exception {
		return roleRepository.findOneByName(name);

	}

	public Group getGroup(String name) throws Exception {
		return groupRepository.findOneByName(name);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void assignSecurity(String user, String role, String group) throws Exception {

		Group g = getGroup(group);
		Role r = getRole(role);
		User u = (User) loadUserByUsername(user);

		assignSecurity(u, r, g);

	}

	public void unAssignSecurity(String user, String role, String group) {

		List<UserSecurity> us = userSecurityRepository.findByUser_UsernameAndRole_NameAndGrp_Name(user, role, group);
		userSecurityRepository.delete(us);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void assignSecurity(String user, String role, String group,  boolean forceCreateGroupAndRole)
			throws Exception {
		Group g = getGroup(group);
		if (forceCreateGroupAndRole && g == null) {
			g = saveOrUpdateGroup(group, "created automatically");
		}
		Role r = getRole(role);

		if (forceCreateGroupAndRole && r == null) {
			r = saveOrUpdateRole(role, "created automcatically");
		}
		User u = (User) loadUserByUsername(user);
		assignSecurity(u, r, g);
	}

	public List<Role> getRoles() throws Exception {
		List<Role> result = new ArrayList<Role>();
		roleRepository.findAll().forEach(new Consumer<Role>() {

			@Override
			public void accept(Role t) {
				result.add(t);
			}

		});

		return result;
	}

	public List<Group> getGroups() throws Exception {
		List<Group> result = new ArrayList<Group>();
		groupRepository.findAll().forEach(new Consumer<Group>() {

			@Override
			public void accept(Group t) {
				result.add(t);

			}

		});

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Group saveOrUpdateGroup(String name, String description) throws Exception {
		Assert.notNull(name, "saveGroup: group is null");

		Group g = getGroup(name);
		if (g != null) {
			g.setDescription(description);
		} else {
			g = new Group();
			g.setName(name);
			g.setDescription(description);
		}
		groupRepository.save(g);
		return g;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role saveOrUpdateRole(String name, String description) throws Exception {
		Assert.notNull(name, "saveRole: role is null");

		Role r = getRole(name);
		if (r != null) {
			r.setDescription(description);
		} else {
			r = new Role();
			r.setName(name);
			r.setDescription(description);

		}
		roleRepository.save(r);

		return r;
	}

	public boolean isUserAllowed(String accessPermission, String username) throws Exception {
		Assert.notNull(username, "isUserAllowed: username is null");
		Assert.notNull(accessPermission, "isUserAllowed: accessPermission is null");
		return isUserAllowed(new String[] { accessPermission }, username);
	}

	public boolean isUserAllowed(String[] accessPermissions, String username) throws Exception {
		return true;
	}

	public List<UserSecurity> getPermissionSpec(String username) throws Exception {
		return userSecurityRepository.findByUser_Username(username);

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void registerUser(User user) throws Exception {
		Assert.notNull(user, "cannot save a null user!!");
		try {
			loadUserByUsername(user.getUsername());
			throw new RuntimeException("User " + user.getUsername() + " already exists");
		} catch (Exception e) {

		}

		userRepository.save(user);

	}

	public void saveOrUpdateUser(User user) {
		userRepository.save(user);
		if (cache_.containsKey(user.getUsername())) {
			cache_.remove(user.getUsername());
		}
	}

	public void deleteGroup(String name) throws Exception {
		Group g = getGroup(name);
		groupRepository.delete(g);

	}

	public void deleteRole(String name) throws Exception {
		Role r = getRole(name);
		roleRepository.delete(r);
	}

	public void deleteUser(String name) throws Exception {

		User u = loadUserByUsername(name);
		userRepository.delete(u);
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {

		List<User> users = new ArrayList<User>();
		userRepository.findAll().forEach(new Consumer<User>() {

			@Override
			public void accept(User t) {
				users.add(t);
			}
		});

		return users;

	}
}
