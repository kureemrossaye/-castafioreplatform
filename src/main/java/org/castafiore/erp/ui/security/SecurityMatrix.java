package org.castafiore.erp.ui.security;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.erp.ActionMatrix;
import org.castafiore.erp.DashBoard;
import org.castafiore.erp.EmployeeGroup;
import org.castafiore.erp.EmployeeMembership;
import org.castafiore.erp.EmployeeRole;
import org.castafiore.erp.security.SecurityService;
import org.castafiore.erp.utils.CSSUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class SecurityMatrix extends EXContainer implements Event, TableModel, CellRenderer{


	private EXSelect<DashBoard> modules;
	
	private EXSelect<String> actions;
	
	private EXButton load = new EXButton("load", "Load Matrix");
	
	private EXButton save = new EXButton("save", "Save Matrix");
	
	private EXTable table;
	
	private SecurityService service;
	
	private List<EmployeeGroup> groups;
	
	private List<EmployeeRole> roles;
	
	private List<EmployeeMembership> matrix;
	
	public SecurityMatrix() {
		super("Matrix", "div");
		service = SpringUtil.getBeanOfType(SecurityService.class);
		jbInit();
	}
	
	public void jbInit(){
		DefaultDataModel<String> modActions = new DefaultDataModel<String>();
		modActions.addItem("Read", "Create", "Edit", "Delete", "Comment");
		actions = new EXSelect<String>("actions", modActions);
		addChild(actions);
		
		DefaultDataModel<DashBoard> modModules = new DefaultDataModel<DashBoard>();
		loadModules(modModules);
		modules = new EXSelect<DashBoard>("modules", modModules);
		addChild(modules);
		
		load.addEvent(this, CLICK);
		addChild(load);
		
		save.addEvent(this, CLICK);
		
		table = new EXTable("table", null);
		table.setCellRenderer(this);
		
		addChild(table);
		
		
	}
	
	protected void loadModules(DefaultDataModel<DashBoard> modules){
		DashBoard db = SpringUtil.getBeanOfType(DashBoard.class);
		modules.addItem(db);
		for(DashBoard module : db.getChildren()){
			modules.addItem(module);
			if(module.getChildren().size() > 0){
				for(DashBoard child : module.getChildren()){
					modules.addItem(child);
				}
			}
		}
	}
	
	public void toggle(Container c){
		int row = Integer.parseInt(c.getAttribute("row"));
		int column = Integer.parseInt(c.getAttribute("column"));
		EmployeeGroup grp =  groups.get(column-1);
		EmployeeRole role = roles.get(row);
		if(c.getStyleClass().contains("membernot")){
			//make a member
			EmployeeMembership m = new EmployeeMembership();
			m.setGrp(grp);
			m.setRole(role);
			
		}
		
	}
	public void loadMatrix()throws Exception{
		
		
		String module = modules.getValue().getModule();
		String action = actions.getValue();
		Class clazz = Class.forName(module);
		ActionMatrix matrix = service.getMatrix(clazz, action);
		if(matrix != null)
			this.matrix = matrix.getMatrix();
		else{
			this.matrix = new ArrayList<EmployeeMembership>();
					
		}
		
		
		roles = service.getRoles();
		groups = service.getGroups();
		table.setModel(this);
	}

	@Override
	public int getRowCount() {
		return roles.size();
	}

	@Override
	public int getColumnCount() {
		return groups.size() + 1;
	}

	@Override
	public int getRowsPerPage() {
		return getRowCount();
	}

	@Override
	public String getColumnNameAt(int index) {
		if(index == 0){
			return "Roles\\Group";
		}else{
			return groups.get(index-1).getCode();
		}
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(col == 0){
			return roles.get(row).getCode();
		}else{
			EmployeeGroup group = groups.get(col-1);
			EmployeeRole role = roles.get(row);
			for(EmployeeMembership m : matrix){
				if(m.getGrp().equals(group) && m.getRole().equals(role)){
					return group;
				}
			}
			
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("load")){
			try{
				loadMatrix();
			}catch(Exception e){
				throw new UIException(e);
			}
			
		}
		
		
		return true;
	}
	
	

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object value = getValueAt(column, row, page);
		
		if(value == null && column > 0){
			Container div = new EXContainer("ds", "div").addClass("membernot").addEvent(this, CLICK).setAttribute("row", row + "").setAttribute("column", column + "");
			return div;
		}
		if(value instanceof String){
			
			
			return new EXContainer("label", "label").setText(value.toString());
		}else if(value instanceof EmployeeMembership){
			Container div = new EXContainer("ds", "div").addClass("memberselected").addEvent(this, CLICK).setAttribute("row", row + "").setAttribute("column", column + "");
			return div;
		}
		return null;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
	}
	
	
	
	
	

}
