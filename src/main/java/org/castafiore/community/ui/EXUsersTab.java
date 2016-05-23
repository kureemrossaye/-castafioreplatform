package org.castafiore.community.ui;

import java.util.Map;

import org.castafiore.community.ui.users.RegisterUserForm;
import org.castafiore.community.ui.users.UsersCellRenderer;
import org.castafiore.community.ui.users.UsersColumnModel;
import org.castafiore.community.ui.users.UsersTableModel;
import org.castafiore.security.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXUsersTab extends EXContainer implements Event{
	private SecurityService service;
	
	private RegisterUserForm form ;

	public EXUsersTab(String name, SecurityService service) {
		super(name, "div");
		this.service = service;
		EXToolBar tb = new EXToolBar("tb");
		EXButton addUser = new EXButton("addUser", "New user");
		addUser.addEvent(this, Event.CLICK);
		
		EXButton refresh = new EXButton("refresh", "Refresh");
		refresh.addEvent(this, Event.CLICK);
		
		EXButtonSet set = new EXButtonSet("bs");
		set.addItem(addUser);
		set.addItem(refresh);
		
		tb.addItem(set);
		addChild(tb);
		tb.setStyle("margin", "8px");
		try{
			EXTable table = new EXTable("usersList", new UsersTableModel(service));
			table.setWidth(Dimension.parse("100%"));
			table.setCellRenderer(new UsersCellRenderer());
			table.setColumnModel(new UsersColumnModel());
			addChild(table.setStyle("padding", "10px 0").setStyle("clear", "left"));
			form = new RegisterUserForm(service);
			addChild(form);
		}catch(Exception e){
			throw new UIException(e);
		}
		
		form.decorateAsToggleButton(addUser);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("refresh")){
			EXTable table = getDescendentOfType(EXTable.class);
			try{
				table.setModel(new UsersTableModel(service));
				table.refresh();
			}catch(Exception e){
				e.printStackTrace();
				throw new UIException(e);
			}
		}else{
		
			
			//container.getAncestorOfType(PopupContainer.class).addPopup(new RegisterUserForm(service).setStyle("z-index", "3000"));
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
