package org.castafiore.erp.ui.grid;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.ui.form.controls.ControlsUtil;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class GridColumn extends EXContainer implements Event{
	private Container link;
	
	private Column column;
	
	private Class<? extends BaseErpModel> vo;
	
	private Container search =null;
	
	private EXTable table;
	
	private Field prop_;
	
	private Container funnel = new EXContainer("filter", "a").setAttribute("href", "#del").addChild(new EXContainer("", "img").setAttribute("src", "style/icons/funnel.png"));
	
	private GridFrame frame_;
	
	public GridColumn(String field, Column column , Class<? extends BaseErpModel> vo, Field prop, java.lang.reflect.Field f, EXTable table, GridFrame frame) {
		super(field, "th");
		
		this.column = column;
		this.frame_ = frame;
		this.vo = vo;
		this.table = table;
		this.prop_ = prop;
		addChild(funnel.addEvent(this, CLICK));
		link = new EXContainer("link", "a").setAttribute("href", "#" + field).setText(column.caption());
		
		addChild(link.setStyle("color", "white"));
		
		
		if(StringUtil.isNotEmpty(column.style())){
			setAttribute("style", column.style());
		}
		
		
		
	}
	
	public void show(){
		if(search == null){
			search = ControlsUtil.getSearchUI(getName(), prop_, vo,frame_);
			addChild(search.addClass("search-form"));
		}else{
			if(search.isVisible()){
				search.setDisplay(false);
			}else{
				search.setDisplay(true);
			}
		}
		
	}
	
	public void hide()
	{
		if(search != null && search.isVisible()){
			search.setDisplay(false);
		}
	}
	

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equalsIgnoreCase("filter")){
			for(Container c : table.getHeaderContainer().getChildren()){
				
				if(c instanceof WorkflowColumn){
					WorkflowColumn column = (WorkflowColumn)c;
					column.hide();
				}
				
				if(c instanceof GridColumn){
					GridColumn column = (GridColumn)c;
					column.hide();
				}
			}
		}
		
		container.getAncestorOfType(GridColumn.class).show();
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}


}
