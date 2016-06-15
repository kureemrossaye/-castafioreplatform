package org.castafiore.erp.ui.grid;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.ui.form.controls.ControlsUtil;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.form.controls.search.WorkflowSearchControl;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.StringUtil;

public class WorkflowColumn extends EXContainer implements Event{
	private Container link;
	
	
	private Class<? extends BaseErpModel> vo;
	
	private WorkflowSearchControl search =null;

	
	private Container funnel = new EXContainer("filter", "a").setAttribute("href", "#del").addChild(new EXContainer("", "img").setAttribute("src", "style/icons/funnel.png"));
	
	private GridFrame frame_;
	
	private Workflow workflow;
	
	
	public WorkflowColumn(Workflow workflow, GridFrame frame) {
		super("wfcol", "th");
		this.workflow = workflow;
		this.frame_ = frame;
		addChild(funnel.addEvent(this, CLICK));
		link = new EXContainer("link", "a").setAttribute("href", "#workflow" ).setText("Status");
		
		addChild(link.setStyle("color", "white"));
		
		setStyle("width", "12%");
		
		
		
		
	}
	
	public void show(){
		if(search == null){
			search = new WorkflowSearchControl(frame_,workflow);
			addChild(search.addClass("search-form").addClass("search-status"));
		}else{
			search.setDisplay(true);
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
			for(Container c : getParent().getChildren()){
				
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
		
		show();
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}


}
