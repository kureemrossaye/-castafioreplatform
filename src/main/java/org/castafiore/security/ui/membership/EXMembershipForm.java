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
 package org.castafiore.security.ui.membership;

import java.util.Map;

import org.castafiore.security.SecurityService;
import org.castafiore.security.model.Role;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class EXMembershipForm extends EXDynaformPanel implements Event{

	private SecurityService service;
	
	private EXButton btnSave = new EXButton("Save", "Save");

	private EXButton btnCancel = new EXButton("cancel", "Cancel");
	
	private EXTable table;

	
	public EXMembershipForm(SecurityService service, EXTable table) {
		super("membershipForm", "Membership");
		this.service = service;
		addField("Name :", new EXInput("name"));
		addField("Description :",new EXTextArea("description"));
		addButton(btnSave);
		addButton(btnCancel);
		
		
		addButton(btnCancel);
		addButton(btnSave);
		btnCancel.setType(EXButton.TYPE_LINK).addEvent(this, CLICK);
		btnSave.setType(EXButton.TYPE_SUCCESS);
		btnSave.addEvent(this, Event.CLICK);
		
		this.table = table;
	}
	
	
	@SuppressWarnings("unchecked")
	public void setMembership(String name)throws Exception{
		if(name != null){
			getField("name").setValue(name);
			((EXInput)getField("name")).setEnabled(false);
			
			Role role = service.getRole(name);
			getField("description").setValue(role.getDescription());
		}else{
			getField("name").setValue("");
			((EXInput)getField("name")).setEnabled(true);
			getField("description").setValue("");
		}
	}
	
	public Role save()throws Exception{
		String name = getField("name").getValue().toString();
		String description = getField("description").getValue().toString();
		if(!StringUtil.isNotEmpty(name)){
			getField("name").addClass("ui-state-error");
		}else{
			Role result = service.saveOrUpdateRole(name, description);
			this.close();
			table.setModel(new MembershipTableModel(service));
			table.refresh();
			return result;
			
		}
		return null;
	}


	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		if(container.equals(btnCancel)){
			close();
		}else{
			try{
			save();
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	

}
