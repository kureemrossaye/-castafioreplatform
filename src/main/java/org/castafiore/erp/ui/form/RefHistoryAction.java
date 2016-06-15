package org.castafiore.erp.ui.form;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Referentiable;
import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.reference.ReferenceManager;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.workflow.Revision;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXGrid.EXRow;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;

public class RefHistoryAction implements FieldAction, Event{
	
	private InputControl field;
	
	private IGroup group;
	
	private Class<? extends BaseErpModel> vo;
	
	@Override
	public EXButtonSet getButtons(InputControl<?> field, IGroup group, Class<? extends BaseErpModel> vo) {
		this.field = field;
		this.group = group;
		this.vo = vo;
		EXIconButton refHistory = new EXIconButton("refHistory","refHistory" );
		refHistory.setAttribute("title", "Shows the document number of previous status of document");
		refHistory.addEvent(this, CLICK);
		refHistory.setStyle("padding", "8px");
		
		EXIconButton activate = new EXIconButton("activate","activate" );
		activate.setAttribute("title", "Lock this code. Once locked, this code cannot be used again.");
		activate.addEvent(this, CLICK);
		activate.setStyle("padding", "8px");
		
		EXButtonSet set = new EXButtonSet("sa");
		
		set.addItem(activate);
		set.addItem(refHistory);
		return set;
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}
	
	private void showHistory(){
		WorkflowManager manager = SpringUtil.getBeanOfType(WorkflowManager.class);
		Integer id = group.getAncestorOfType(Forms.class).getData().getId();
		
		if(id != null){
			List<Revision> revisions =  manager.getWorkflowHistory(vo, id);
			Workflow workflow = manager.getWorkflow(vo);
			EXGrid grid = new EXGrid("i", 2, 0);
			grid.setStyle("width", "100%");
			for(Revision r : revisions){
				Referentiable ref = (Referentiable)r.getInstance();
				State status = workflow.getState(ref.getStatus());
				
				String label = status.getLabel() + " No.";
				Container uilabel = new EXContainer("i", "label").setStyle("margin", "0").setText(label).setStyle("color", status.getColor());
				
				EXInput i= new EXInput("i");
				i.setValue(ref.getReferenceNumber());
				
				EXRow newRow = grid.addRow();
				newRow.addInCell(0, uilabel);
				newRow.addInCell(1, i.setStyle("margin", "0"));
				
				//field.getParent().addChildAt( i.addClass("form-control input-group").setStyle("width", "100%").setAttribute("disabled", "disabled"),0);
				//field.getParent().addChildAt(uilabel, 0);
			}
			field.getParent().addChild(grid);
			//field.getParent().setRendered(false);
		}
	}
	
	
	private void removeHistory(){
		List<Container> toremove = new LinkedList<Container>();
		for(Container c : field.getParent().getChildren()){
			if(c.getName().equals("i"))
				toremove.add(c);
		}
		for(Container c : toremove){
			c.remove();
		}
	}
	
	private void lockCode(){
		BigInteger defaultStatus = group.getAncestorOfType(Forms.class).getDefaultStatus();
		Object val =ReferenceManager.lockNextCode(vo,group,defaultStatus).toString();
		field.setValue(val);
		field.setEnabled(false);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		
		if(container.getName().equals("refHistory")){
			if("visible".equals(container.getAttribute("history"))){
				removeHistory();
				container.setAttribute("history", "notvisible");
			}else{
				showHistory();
				container.setAttribute("history", "visible");
			}
		}else{
			lockCode();
		}
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	@Override
	public void changeMode(EXButtonSet buttons, ActionScope scope,	InputControl<?> field, IGroup group,
			Class<? extends BaseErpModel> vo) {
		List<Container> toremove = new LinkedList<Container>();
		for(Container c : field.getParent().getChildren()){
			if(c.getName().equals("i"))
				toremove.add(c);
		}
		for(Container c : toremove){
			c.remove();
		}
		
		
		if((scope == ActionScope.all ) || scope == ActionScope.edit){
			//edit mode
			buttons.getDescendentByName("refHistory").setStyle("display", "inline-block");
			buttons.getDescendentByName("activate").setStyle("display", "none");
		}else if ((scope == ActionScope.all) || scope == ActionScope.create){
			//create mode
			
			buttons.getDescendentByName("refHistory").setStyle("display", "none");
			buttons.getDescendentByName("activate").setStyle("display", "inline-block");
		}
		
	}


}
