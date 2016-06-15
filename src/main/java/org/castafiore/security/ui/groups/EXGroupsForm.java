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
package org.castafiore.security.ui.groups;

import org.castafiore.portal.ui.data.EXDataForm;
import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Group;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;

public class EXGroupsForm extends EXDataForm<Group>  {

	private SecurityService service;

	

	public EXGroupsForm(SecurityService service) {
		super("Group", "Group");
		this.service = service;
		addField("Name", new EXInput("name"));
		addField("Description", new EXTextArea("description"));
		
	}

	@SuppressWarnings("unchecked")
	public void setModel(Group group)  {
		String name = group.getName();
		if (name != null) {
			getField("name").setValue(name);
			((EXInput) getField("name")).setEnabled(false);
			getField("description").setValue(group.getDescription());
			setTitle("Edit Group");
		}else{
			super.getField("name").setValue("");
			super.getField("description").setValue("");
			setTitle("Create new group");
		}
	}
	
	public Group getModel(){
		try{
			return doSave();
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	public Group doSave() throws Exception {
		String name = getField("name").getValue().toString();
		String description = getField("description").getValue().toString();
		Group group = service.saveOrUpdateGroup(name, description);
		group.setDescription(description);

		return group;

	}

	@Override
	public void validate() {
		
	}

	@Override
	public void reset() {
		setModel(new Group());
		
	}


}
