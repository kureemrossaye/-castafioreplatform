package org.castafiore.portal.ui.data;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXDataGridToolbar<T> extends EXContainer implements Event {

	private Container menu = new EXContainer("menu", "ul").addClass("mediamgr_menu");

	private Container previous = new EXContainer("previous", "a").addClass("btn").addClass("prev")
			.setText("<span class=\"icon-chevron-left\"></span>");

	private Container next = new EXContainer("next", "a").addClass("btn").addClass("next")
			.setText("<span class=\"icon-chevron-right\"></span>");

	private Container selectAll = new EXContainer("selectAll", "a").addClass("btn").addClass("selectall")
			.setText("<span class=\"icon-check\"></span>Select All");

	private Container edit = new EXContainer("edit", "a").addClass("btn").addClass("newfolder")
			.setAttribute("title", "Create New").setText("<span class=\"icon-folder-open\"></span>");

	private Container delete = new EXContainer("delete", "a").addClass("btn").addClass("trash")
			.setAttribute("title", "Delete Selected Items").setText("<span class=\"icon-trash\"></span>");

	private EXInput searchInput = (EXInput) new EXInput("searchInput").setStyleClass("filekeyword")
			.setAttribute("placeholder", "Search here..");

	private Container searchForm = new EXContainer("searchForm", "div").addChild(searchInput);

	private Container addNew = new EXContainer("addNew", "a").addClass("btn").addClass("btn-primary")
			.setText("Add New Item");
	
	
	
	private EXDataGrid<T> grid_;
	
	private int row =0;
	private int page = 0;

	private int pageSize = 10;
	
	public EXDataGridToolbar(String name) {
		super(name, "div");

		addClass("mediamgr_head");

		addChild(menu);

		addItem(previous.addEvent(this, CLICK));
		addItem(next.addEvent(this, CLICK));
		//addItem(selectAll.addEvent(this, CLICK)).addClass("marginleft15");
		addItem(edit.addEvent(this, CLICK)).addClass("marginleft15").addClass("newfoldbtn");
		addItem(delete.addEvent(this, CLICK)).addClass("marginleft5 trashbtn");
		addItem(searchForm).addClass("marginleft15 filesearch");
		addItem(addNew.addEvent(this, CLICK)).addClass("right newfilebtn");

		addChild(new EXContainer("", "div").addClass("clearall"));
	}
	
	public void setup(EXDataGrid<T> grid, DataGridController<T> controller){
		this.grid_ = grid;
		TableModel model = grid_.getTable().getModel();
		pageSize = model.getRowsPerPage();
		grid_.getTable().selectRow(0, 0);
		
	}
	
	public void selectNext(){
		row++;
		
		if(row >= pageSize){
			page++;
			row = row-pageSize;
		}
		
		this.grid_.getTable().selectRow(row, page);
	}
	
	public void selectPrevious(){
		if(row <= 0 && page <= 0){
			return;
		}
		
		if(row == 0){
			row = 10;
			page--;
		}
		row--;
		this.grid_.getTable().selectRow(row, page);
		
	}

	public Container getPrevious() {
		return previous;
	}

	public Container getNext() {
		return next;
	}

	public Container getSelectAll() {
		return selectAll;
	}

	public Container getEdit() {
		return edit;
	}

	public Container getDelete() {
		return delete;
	}

	public EXInput getSearchInput() {
		return searchInput;
	}

	public Container getSearchForm() {
		return searchForm;
	}

	public Container getAddNew() {
		return addNew;
	}
	
	public void edit(){
		List<Integer[]> rows = this.grid_.getTable().getSelectedRows();
		
		Integer[] row = rows.get(0);
		T oROw = this.grid_.getDataLocator().getRow(row[0], row[1]);
		this.grid_.edit(oROw);;
		
	}

	protected Container addItem(Container item) {
		Container li = new EXContainer("", "li");
		li.addChild(item);
		menu.addChild(li);
		return li;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		if(container.equals(next)){
			selectNext();
		}else if(container.equals(previous)){
			selectPrevious();
		}else if(container.equals(addNew)){
			grid_.addNew();
		}else if(container.equals(edit)){
			edit();
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {
		
	}
	


}
