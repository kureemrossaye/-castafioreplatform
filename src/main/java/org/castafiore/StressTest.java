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
 package org.castafiore;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXModal;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("os")
@Scope("session")
public class StressTest extends EXApplication {
	
	private static final long serialVersionUID = 1L;
	
	public StressTest() {
		super("os");
		EXModal modal = new EXModal("modal", "This is a test modal");
		
		addChild(modal);
		modal.setBody(getTabs());
		
		EXPanel panel = new EXPanel("Test", "Test Panel");
		
		
		
		
		
		addChild(panel);
		panel.setBody(getTable());
		
		
		
		
		EXButton btn = new EXButton("test", "Open Modal");
		btn.setAttribute("data-toggle", "modal");
		btn.setAttribute("data-target", "#" + modal.getId());
		//addChild(btn);
		
		panel.getFooterContainer().addChild(btn);
	}
	
	
	
	public EXTabPanel getTabs (){
		EXTabPanel panel = new EXTabPanel("Test Panel");
		final String[] labels = new String[]{"Main", "Description", "Permissions"};
		panel.setModel(new TabModel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int size() {
				return labels.length;
			}
			
			@Override
			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
				return labels[index];
			}
			
			@Override
			public Container getTabContentAt(TabPanel pane, int index) {
				EXFieldSet set = new EXFieldSet("set", "Field set");
				set.addField("Username", new EXInput("username"));
				set.addField("Password", new EXPassword("password"));
				return set;
			}
			
			@Override
			public int getSelectedTab() {
				return 0;
			}
		});
		
		return panel;
	}
	
	
	
	private EXTable getTable(){
		
		final String[] cols = new String[]{"username", "First Name", "Last Name", "Phone", "Role"};
		
		EXTable table = new EXTable("table", new TableModel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
			
			@Override
			public Object getValueAt(int col, int row, int page) {
				return cols[col] + " " + ((page*10) + row);
			}
			
			@Override
			public int getRowsPerPage() {
				return 10;
			}
			
			@Override
			public int getRowCount() {
				return 100;
			}
			
			@Override
			public String getColumnNameAt(int index) {
				return cols[index];
			}
			
			@Override
			public int getColumnCount() {
				return cols.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		});
		table.setStriped(true);
		table.setCaption("Sample table");
		table.setCondensed(true);
		table.setHover(true);
		return table;
		
		
	}

}
