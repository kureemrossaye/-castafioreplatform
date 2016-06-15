package org.castafiore.erp.ui.form.controls.editable;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.ui.form.Error;
import org.castafiore.erp.ui.form.controls.DecimalControl;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.form.controls.IntegerControl;
import org.castafiore.erp.ui.form.validation.ValidationUtil;
import org.castafiore.erp.ui.grid.NavigatableGrid;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EditableTableModel;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

public class EditableControl<T> extends EXContainer implements Event{
	
	private T data;
	
	private Container read = new EXContainer("a", "a").setAttribute("href", "#").setStyle("width", "100%").setStyle("padding", "0 5px").addClass("editable");
	
	private InputControl<T> edit;
	
	private Class<? extends BaseErpModel> vo;
	
	private int row ;
	
	private int col;
	
	private EditableTableModel table;
	
	private boolean editable = true;
	public EditableControl(String name, InputControl<T> edit, Class<? extends BaseErpModel> vo, EditableTableModel table, int row, int col) {
		super(name, "div");
		addChild(read);
		read.addEvent(this, CLICK);
		this.edit = edit;
		this.vo = vo;
		this.table = table;
		this.row = row;
		this.col = col;
		
		if((edit instanceof IntegerControl) || edit instanceof DecimalControl){
			read.setStyle("text-align", "right");
		}
		
		
		
		read.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				container.getParent().getParent().addClass("selectedcell");
				//container.eval("this.select()");
				
			}
		}, Event.FOCUSS);
		
		read.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				container.getParent().getParent().removeClass("selectedcell");
				
			}
		}, Event.BLUR);
		
read.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				edit();
				return true;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				//container.getParent().getParent().removeClass("selectedcell");
				container.server(this);
				
			}
		}, Event.KEY_DOWN);
		
		edit.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				if(request.containsKey("error")){
					JMap options = new JMap();
					
					options.put("show",true).put("content",request.get("error"))
					.put("position", new JMap().put("my", "bottom center").put("at","top center"))
					.put("hide", new JMap().put("event", "click"))
					.put("styles", new JMap().put("classes", "qtip-red"));
					
					container.invoke("qtip", options);
					container.focus(container.clone());
				}else{
					container.invoke("qtip", "destroy", new Var("true"));
					container.append(new JQuery("#" + request.get("focus")).invoke("focus"));
				}
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				if(request.containsKey("esc")){
					cancel();
					return true;
				}
				
				Error error = ok();
				if(error != null){
					request.put("error", error.getMessage());
				}else{
					NavigatableGrid table = container.getAncestorOfType(NavigatableGrid.class);
					Container next = table.getNextTd(container.getAncestorOfType(EditableControl.class).col, container.getAncestorOfType(EditableControl.class).row);
					
					
					EditableControl<?> nextControl = next.getDescendentOfType(EditableControl.class);
					nextControl.edit();
					request.put("focus", nextControl.edit.getId());
				}
				return true;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				//container.eval("alert(event.keyCode)");
				container.IF(new Var("event.keyCode").equal(new Var("13")), container.clone().mask().server(this), //tab
						
						container.clone().IF(new Var("event.keyCode").equal(new Var("9")), container.clone().mask().server(this),//enter 
								
								container.clone().IF(new Var("event.keyCode").equal(new Var("27")), container.clone().mask().makeServerRequest( new JMap().put("esc", "true"), this),container.clone())//escape
						)
				);
				
				
			}
		}, Event.KEY_PRESS);
	
	
	
	}
	
	
	public void setEditable(boolean b){
		this.editable = b;
	}
	
	public void setData(T data){
		this.data = data;
		if(data != null)
			read.setText(data.toString());
		else
			read.setText("");
		edit.setValue(data);
	}
	
	
	public void edit(){
		
		if(editable){
			if(this.getChildren().size() == 1){
				addChild(edit);
			}
			
			edit.setDisplay(true);
			read.setDisplay(false);
		}
	}
	
	public Error validate(){
		java.lang.reflect.Field field =ReflectionUtils.findField(vo, getName());
		Field prop =field.getAnnotation(Field.class);

		if(prop.required()){
			org.castafiore.erp.ui.form.Error error = ValidationUtil.getValidator("required").validate(field, edit.getValue());
			if(error != null){
				edit.addClass("qtip-red");
				return error;
			}
			
		}
		
		if(prop.max() != Integer.MIN_VALUE || prop.min() != Integer.MAX_VALUE){
			org.castafiore.erp.ui.form.Error error = ValidationUtil.getValidator("range").validate(field, edit.getValue());
			if(error != null){
				edit.addClass("qtip-red");
				return error;
			}
		}
		
		String[] validators = ReflectionUtils.findField(vo, getName()).getAnnotation(Field.class).validators();
		
		for(String validator : validators){
			org.castafiore.erp.ui.form.Error error = ValidationUtil.getValidator(validator).validate(field, edit.getValue());
			if(error != null){
				edit.addClass("qtip-red");
				return error;
			}
		}
		
		edit.removeClass("qtip-red");
		return null;
		
	}
	
	
	
	public Container getRead() {
		return read;
	}


	public void setRead(Container read) {
		this.read = read;
	}


	public InputControl<T> getEdit() {
		return edit;
	}


	public void setEdit(InputControl<T> edit) {
		this.edit = edit;
	}


	public Class<? extends BaseErpModel> getVo() {
		return vo;
	}


	public void setVo(Class<? extends BaseErpModel> vo) {
		this.vo = vo;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}


	


	public T getData() {
		return data;
	}


	public Error ok(){
		Error error = validate();
		if(error == null){
			edit.setDisplay(false);
			read.setDisplay(true);
			data = edit.getValue();
			table.setValueAt(col, row, 0, data);
			if(data != null)
				read.setText(data.toString());
			else
				read.setText("");
			
			
		}
		return error;
	}
	
	public void cancel(){
		edit.setDisplay(false);
		read.setDisplay(true);
	}


	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getId().equals(read.getId())){
			edit();
			request.put("edit", "true");
		}else{
			Error error = ok();
			if(error != null){
				request.put("error", error.getMessage());
			}
		}
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("edit")){
			container.append(new JQuery("#" + edit.getId()).invoke("focus"));
			
		}
		if(request.containsKey("error")){
			JMap options = new JMap();
			
			options.put("show",true).put("content",request.get("error"))
			.put("position", new JMap().put("my", "bottom center").put("at","top center"))
			.put("hide", new JMap().put("event", "click"))
			.put("styles", new JMap().put("classes", "qtip-red"));
			
			container.invoke("qtip", options);
		}else{
			container.invoke("qtip", "destroy", new Var("true"));
		}
		
	}

	
	

}
