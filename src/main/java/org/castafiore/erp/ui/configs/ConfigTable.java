package org.castafiore.erp.ui.configs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.FieldType;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.form.IGroup;
import org.castafiore.erp.ui.form.NoInterceptor;
import org.castafiore.erp.ui.form.controls.ControlsUtil;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.LabelControl;
import org.castafiore.erp.ui.form.controls.editable.EditableControl;
import org.castafiore.erp.ui.grid.DefaultDataLocator;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.grid.NavigatableGrid;
import org.castafiore.erp.ui.grid.QueryParams;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.table.EXEditableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.EditableCellRenderer;
import org.castafiore.ui.ex.form.table.EditableTableModel;
import org.castafiore.ui.ex.form.table.RowDecorator;
import org.castafiore.ui.ex.form.table.TableColumnModel;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class ConfigTable extends EXContainer implements TableColumnModel, EditableTableModel, EditableCellRenderer, RowDecorator,NavigatableGrid, IGroup{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EXTable table;
	private EXButtonSet toolbar = new EXButtonSet("toolbar");
	

	
	private Table tableDefn;
	Class<? extends BaseErpModel> vo_;

	
	private List<BaseErpModel> model;
	
	
	private GridController controller_;
	
	private int selectedRow = -1;
	
	private Map<InputControl<?>, List<String>> validators = new HashMap<InputControl<?>, List<String>>();
	

	public ConfigTable( Class<? extends BaseErpModel> vo, GridController controller) {
		super("ConfigTable", "div");
		toolbar.addClass("btn-xs");
		this.vo_ = vo;
		this.controller_ = controller;
		init();
	}

	
	@Override
	public void decorateRow(int rowCount, Container row, org.castafiore.ui.ex.form.table.Table table,
			TableModel model) {
		int count = 0;
		for(String field : ReflectionUtils.getAnnotation(vo_,Table.class).columns()){
			Field f = ReflectionUtils.findField(vo_, field).getAnnotation(Field.class);
			if(f.type() == FieldType.Int || f.type() == FieldType.Float || f.type() == FieldType.money){
				row.getChildByIndex(count).setStyle("text-align", "right");
			}
			count++;
		}
		

	}
	
	
	public void init(){
		DefaultDataLocator locator = new DefaultDataLocator();
		QueryParams params = new QueryParams();
		params.setShowAll(true);
		List<BaseErpModel> value = locator.loadData(vo_, params).getData();
		this.model = value;
		tableDefn = ReflectionUtils.getAnnotation(vo_,Table.class);
		
		//this.model = new ArrayList<BaseErpModel>();
		
		table = new EXTable(getName() + "_table", this );
		
		table.setRowDecorator(this);
		table.setColumnModel(this);
		//table.setModel(this);
		table.setBordered(true);
		table.setCondensed(true);
		table.setStriped(true);
		table.setShowFooter(false);
		table.setHover(true);
		table.setCellRenderer(this);
		
		
		table.setCaption("");
		table.setStyle("margin", "0px");
		
		
		addChild(table);
	}
	
	

	@Override
	public Container getComponentAt(int row, int column, int page,	TableModel model, EXTable table) {
		Object data = model.getValueAt(column, row, page);
		String property = tableDefn.columns()[column];
		
		Field colField = ReflectionUtils.findField(vo_, property).getAnnotation(Field.class);
		if(colField.editable()){
			
			
			InputControl edit = getEditUI(property, colField);
			
			EditableControl control = new EditableControl(property, edit, vo_,this, row, column);
			control.setData(data);
			
			if(data != null && !colField.updateable()){
				control.setEditable(false);
			}else{
				control.setEditable(true);
			}
			return control;
		}else{
			String s = "";
			if(data != null)
				s =data.toString();
			
				
				LabelControl control = new LabelControl(property, s);
				return control;
		}

		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Object data = model.getValueAt(column, row, page);
		String property = tableDefn.columns()[column];
		
		Field colField = ReflectionUtils.findField(vo_, property).getAnnotation(Field.class);
		if(colField.editable()){
			
			EditableControl control = (EditableControl)component;//new EditableControl(property, edit, lookup,this, row, column);
			control.setData(data);
			
			if(data != null && !colField.updateable()){
				control.setEditable(false);
			}else{
				control.setEditable(true);
			}
		}else{
			String s = "";
			if(data != null)
				s =data.toString();
			
				
				LabelControl control = (LabelControl)component;//new LabelControl(property, s);
				control.setValue(s);
		}

	}

	@Override
	public int getRowCount() {
		int size = model.size();
		if(size < 10){
			return 10;
		}else{
			return size + 1;
		}
	}

	@Override
	public int getColumnCount() {
		return tableDefn.columns().length;
	}
   
	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public String getColumnNameAt(int index) {
		String property = tableDefn.columns()[index];
		Column columnDefn = ReflectionUtils.findField(vo_,property).getAnnotation(Column.class);
		return columnDefn.caption();
	}
	
	
	public Container getNextTd(int col, int row){
		String[] columns = ReflectionUtils.getAnnotation(vo_,Table.class).columns();
		boolean addRow = false;
		int newCol = col;
		int newRow = row;
		boolean tosave = false;
		while(true){
			newCol = newCol +1;
			if(newCol < columns.length){
				
			}else{
				if(addRow)
					return null;
				
				addRow = true;
				newCol = 0;
				newRow = row +1;
				tosave = true;
			}
			String property = columns[newCol];
			Field f = ReflectionUtils.findField(vo_,property).getAnnotation(Field.class);
			if(f.editable()){
				break;
			}
		}
		
		if(addRow){
			try{
				this.model.add((BaseErpModel)vo_.newInstance());
				if(model.size() > table.getRowCount()){
					table.addRow();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		try{
			if(col == 1){
				BaseErpModel instance = model.get(row);
				if(instance.getId() == null)
					this.controller_.insertRecord(instance);
				else
					this.controller_.updateRecord(instance);
			
				
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return table.getTd(newCol, newRow);
		
		
	}
	
	

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(row >= model.size()){
			try{
				//model.add((BaseErpModel)this.propertyDefn.lookupModel().newInstance());
				//return getValueAt(col, row, page);
				return null;
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}else{
			String property = tableDefn.columns()[col];
			return BeanUtil.getProperty(model.get(row), property);
		}
	} 

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		String property = tableDefn.columns()[columnIndex];
		return ReflectionUtils.findField(vo_,property).getType();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		String property = tableDefn.columns()[columnIndex];
		 Column columnDefn = ReflectionUtils.findField(vo_,property).getAnnotation(Column.class);
		 return columnDefn.editable();
		 
	}

	@Override
	public StatefullComponent<?> getInputAt(int row, int column, int page,
			EditableTableModel model, EXEditableTable table) {
		
			String property = tableDefn.columns()[column];
			 Field field = ReflectionUtils.findField(vo_,property).getAnnotation(Field.class);
			 
			 StatefullComponent<?> input = getEditUI(property, field);
			 this.selectedRow = row;
			 return input;
	}
	
	
	
	private InputControl<?> getEditUI(String field,Field prop){
		InputControl<?> uifield = ControlsUtil.getField(field, prop, this,controller_);
		ControlsUtil.applyInterceptors(uifield, this, prop);
		ControlsUtil.applyValidators(uifield, this, prop);
		return uifield;
		
	}
	
	public void addValidator(InputControl<?> input, String validator){
		if(validators.containsKey(input)){
			if(!validators.get(input).contains(validator))
				validators.get(input).add(validator);
		}else{
			validators.put(input, new LinkedList<String>());
			addValidator(input, validator);
		}
	}

	@Override
	public void setValueAt(int col, int row, int page, Object value) {
		int realRow = (page*getRowsPerPage()) +row;
		if(realRow == model.size()){
			try{
			model.add((BaseErpModel)vo_.newInstance());
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		BaseErpModel data = model.get((page*getRowsPerPage()) +row);
		
		String property = tableDefn.columns()[col];
		
		BeanUtil.setProperty(data, property, value);
		
		table.refreshCell(row, col);
	}




	

	@Override
	public Container getColumnAt(int index,
			org.castafiore.ui.ex.form.table.Table table, TableModel model) {
		EXContainer column = new EXContainer("" + index, "th");
		String property = this.tableDefn.columns()[index];
		
		Column field = ReflectionUtils.findField(vo_,property).getAnnotation(Column.class);
		Field f = ReflectionUtils.findField(vo_, property).getAnnotation(Field.class);
		if(f.type() == FieldType.Int || f.type() == FieldType.Float || f.type() == FieldType.money){
			column.setStyle("text-align", "right");
		}
		
		if(StringUtil.isNotEmpty( field.style())){
			column.setAttribute("style", field.style());
		}
		column.setText(model.getColumnNameAt(index));
		return column;
	}

	
	
	public void setValue(Object value,  String field){
		BaseErpModel object = this.model.get(selectedRow);
		BeanUtil.setProperty(object, field, value);
		int index =-1;
		for(int i = 0; i < tableDefn.columns().length; i++){
			if(tableDefn.columns()[i].equals(field)){
				index = i;
				break;
			}
		}
		
		Container component = table.getChild("tbody").getChildByIndex(selectedRow).getChildByIndex(index).getChildByIndex(0);
		
		onChangePage(component, selectedRow	,index, 0,this, table);
	}
	
	public Object getValue(String field){
		int index = -1;
		for(int i = 0; i < tableDefn.columns().length; i++){
			if(tableDefn.columns()[i].equals(field)){
				index = i;
				break;
			}
		}
		
		if(index >=0){
			return getValueAt(index, selectedRow, 0);
		}
		return null;
	}


	@Override
	public InputControl<?> getField(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void fillModel(BaseErpModel model) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setData(BaseErpModel model) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Map<InputControl<?>, List<String>> getValidators() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}



	
}
