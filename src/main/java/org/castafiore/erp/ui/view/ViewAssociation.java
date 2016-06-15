package org.castafiore.erp.ui.view;

import java.lang.reflect.Field;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class ViewAssociation extends EXContainer implements View, Event , Draggable{

	private Class<? extends BaseErpModel> vo;
	
	private Field field;
	
	private Class<?> lookup;
	
	private Container link = new EXContainer("link", "a").setAttribute("href", "#");
	
	private ViewFrame frame = null;
	
	private BaseErpModel value = null;
	
	private GridController controlle_;
	
	public ViewAssociation(String field, Class<? extends BaseErpModel> vo,GridController controller ) {
		super(field, "div");
		this.vo = vo;
		this.controlle_ = controller;
		this.field = ReflectionUtils.findField(vo, field);
		this.lookup = this.field.getAnnotation(org.castafiore.erp.annotations.Field.class).lookupModel();
		addChild(link);
		link.addEvent(this, CLICK);
		addClass("view-association");
	}
	
	public void setData(BaseErpModel data){
		value = (BaseErpModel)BeanUtil.getProperty(data, field.getName());
	
		if(value != null)
			link.setAttribute("entity", value.getClass().getName()).setText(value.toString()).setAttribute("oid", value.getId() + "");
		else
			link.setText("");
	}
	
	public BaseErpModel getValue(){
		return value;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(frame == null){
			frame = new ViewFrame((Class)lookup,controlle_);
			addChild(frame.setStyle("position", "absolute"));
			frame.setStyle("z-index", "3000").setStyle("width", "500px").setStyle("max-height", "250px").setStyle("overflow", "auto").addClass("table");
			frame.setData(value);
		}else{
		
			frame.setDisplay(!frame.isVisible());
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JMap getDraggableOptions() {
		JMap options = new JMap();
		options.put("appendTo", "body");
		options.put("revert", true);
		return options;
	}

	
	

}
