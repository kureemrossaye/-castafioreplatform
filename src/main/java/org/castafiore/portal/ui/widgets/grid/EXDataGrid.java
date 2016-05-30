package org.castafiore.portal.ui.widgets.grid;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXDataGrid<T> extends EXContainer{

	private EXGridToolbar<T> toolbar = new EXGridToolbar<T>("toolbar");
	
	private EXTable table;

	public EXDataGrid(String name) {
		super(name, "div");
		
	}
	
	
	public void setupTable(TableModel model, CellRenderer cellRenderer, TableColumnModel columnModel, GridController<T> controller){
		addChild(toolbar);
		table = new EXTable("table", model, cellRenderer);
		table.setColumnModel(columnModel);
		addChild(table);
		toolbar.setup(table, controller);
	}
	
	public void setupTable(EXTable table, GridController<T> controller){
		this.table = table;
		addChild(this.table);
		toolbar.setup(this.table, controller);
	}

	
	

}
