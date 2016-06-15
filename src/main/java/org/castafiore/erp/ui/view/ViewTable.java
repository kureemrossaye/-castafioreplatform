package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.grid.GridController;
//import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class ViewTable extends EXContainer implements TableModel ,View{

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	private Class<?> lookup;
	
	private List<BaseErpModel> model;
	
	private EXTable table = new EXTable("table",null);
	
	private BaseErpModel data = null;
	
	private GridController controller_;
	
	public ViewTable(String field, Class<? extends BaseErpModel> vo, GridController controller) {
		super(field, "div");
		this.vo = vo;
		this.controller_ = controller;
		this.field = ReflectionUtils.findField(vo, field);
		
		this.lookup = this.field.getAnnotation(org.castafiore.erp.annotations.Field.class).lookupModel();
		addChild(table);
	}
	
	public void setData(BaseErpModel data){
		this.data = data;
		List l = (List)BeanUtil.getProperty(data, field.getName());
		if(l!= null)
			model = l;
		else
			model = new ArrayList<BaseErpModel>();
		table.setModel(this);
	}

	@Override
	public int getColumnCount() {
		return lookup.getAnnotation(Table.class).columns().length;
	}

	@Override
	public int getRowsPerPage() {
		return model.size();
	}

	@Override
	public String getColumnNameAt(int index) {
		return ReflectionUtils.findField(lookup, lookup.getAnnotation(Table.class).columns()[index]).getAnnotation(Column.class).caption();
		 // 
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		BaseErpModel m = (model.get(row));
		String property = lookup.getAnnotation(Table.class).columns()[col];
		return BeanUtil.getProperty(m, property);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return ReflectionUtils.findField(lookup, lookup.getAnnotation(Table.class).columns()[columnIndex]).getType();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getRowCount() {
		try{
			return model.size();
		}catch(Exception e){
			data = controller_.activate(data);
			List l = (List)BeanUtil.getProperty(data, field.getName());
			if(l!= null)
				model = l;
			else
				model = new ArrayList<BaseErpModel>();
			
			return model.size();
			
		}
	}

	

}
