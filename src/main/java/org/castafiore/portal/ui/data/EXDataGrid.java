package org.castafiore.portal.ui.data;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableColumnModel;

public class EXDataGrid<T> extends EXContainer {

	private EXDataGridToolbar<T> toolbar = new EXDataGridToolbar<T>("toolbar");

	private EXTable table;

	private DataForm<T> form;

	private Container container = new EXContainer("header", "div");

	private Class<T> clazz_;
	
	private DataGridController<T> dataController;
	
	private DataLocator<T> locator;

	private EXDataGrid(Class<T> clazz) {
		super(clazz.getSimpleName(), "div");
		clazz_ = clazz;
		addChild(container);
		addClass("EXDataGrid");
	}

	public EXDataGrid(Class<T> clazz,DataLocator<T> model, CellRenderer cellRenderer, TableColumnModel columnModel,
			DataGridController<T> controller, DataForm<T> form) {
		
		this(clazz);
		this.locator = model;
		EXTable table = new EXTable("table", model, cellRenderer);
		table.setColumnModel(columnModel);
		setupTable(table, controller);
		setupForm(form);
	}

	
	public EXDataGrid(Class<T> clazz, DataLocator<T> locator,
			DataGridController<T> controller, DataForm<T> form) {
		
		this(clazz);
		this.locator = locator;
		EXTable table = new EXTable("table", locator);
		setupTable(table, controller);
		setupForm(form);
	}

	protected void setupTable(EXTable table, DataGridController<T> controller) {
		this.table = table;
		this.table.setBordered(true);
		container.getChildren().clear();
		container.setRendered(false);
		container.addChild(toolbar);
		container.addChild(table);
		toolbar.setup(this, controller);
	}

	protected void setupForm(DataForm<T> form) {
		this.form = form;
		addChild(form);
		form.setDataGrid(this);
		form.setDisplay(false);
	}
	
	public DataLocator<T> getDataLocator(){
		return locator;
	}
	
	public DataGridController<T> getController(){
		return this.dataController;
	}

	public void addNew() {
		form.setDisplay(true);
		form.reset();
		container.setDisplay(false);
		try {
			form.setModel(clazz_.newInstance());
		} catch (Exception e) {
			throw new UIException(e);
		}
	}

	

	public EXDataGridToolbar<T> getToolbar() {
		return toolbar;
	}

	public EXTable getTable() {
		return table;
	}

	public DataForm<T> getForm() {
		return form;
	}
	
	public void edit(T item){
		container.setDisplay(false);
		form.setDisplay(true);
		form.setModel(item);
	}
	
	

	public void cancel() {
		form.setDisplay(false);
		form.reset();
		container.setDisplay(true);
	}
	
	public void finish() {
		container.setDisplay(true);
		form.setDisplay(false);
		locator.refresh();
		table.setModel(locator);
	}

}
