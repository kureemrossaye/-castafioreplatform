package org.castafiore.security.ui.groups;

import java.util.Map;

import org.castafiore.security.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.EXTable.RowSelectionListener;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXGroupsTab extends EXContainer implements Event, RowSelectionListener{
	

	private EXGroupsForm form = null;
	
	private EXTable table;
	
	private EXButton addNew = new EXButton("addNew", "Add New");
	
	private EXButton edit = new EXButton("edit", "Edit");
	
	private EXButton delete = new EXButton("delete", "Delete");
	
	private SecurityService service;
	
	public EXGroupsTab(String name, SecurityService service) {
		super(name, "div");
		try{
			this.service = service;
			EXToolBar toolbar = new EXToolBar("tb");
			EXButtonSet set = new EXButtonSet("set");
			set.addItem(addNew).addItem(edit).addItem(delete);
			toolbar.addItem(set);
			edit.setEnabled(false);
			delete.setEnabled(false);
			delete.addEvent(this, CLICK);
			edit.addEvent(this, CLICK);
			addNew.addEvent(this, CLICK);
			addChild(toolbar);
			
			
			table = new EXTable("groupsList", new GroupsTableModel(service));
			table.setCellRenderer(new GroupsCellRenderer());
			table.setColumnModel(new GroupsColumnModel());
			table.addRowSelectionListener(this);
			addChild(table);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		form = new EXGroupsForm(service, table);
		addChild(form);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		
		if(container.getId().equals(delete.getId())){
			container.alert("Do you really want to delete this group", container.clone().server(this));
		}else{
			container.server(this);
		}
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.equals(addNew)){
			try{
				
				form.setGroup(null);
				form.open();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}else if(container.equals(edit)){
			try{
				Integer[] index = table.getSelectedRows().get(0);
				
				String g = table.getModel().getValueAt(0, index[0], index[1]).toString();
				
				
				form.setGroup(g);
				form.open();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}else{
			
			String name = table.getModel().getValueAt(0, table.getSelectedRows().get(0)[0],  table.getSelectedRows().get(0)[1]).toString();
			try{
			service.deleteGroup(name);
			table.setModel(new GroupsTableModel(service));
			}catch(Exception e){
				e.printStackTrace();
				throw new UIException(e);
			}
			//delete. need alert first
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	@Override
	public void onRowSelected(Table table, TableModel model, int row, int page) {
		edit.setEnabled(true);
		delete.setEnabled(true);
		
	}

	@Override
	public void onRowDeSelected(Table table, TableModel model, int row, int page) {
		edit.setEnabled(false);
		delete.setEnabled(false);
	}
	
	

}
