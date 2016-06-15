package org.castafiore.erp.ui.form.controls.search;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.grid.WorkflowColumn;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXCheckBox;

public class WorkflowSearchControl extends EXContainer implements Event{
	

	
	private GridFrame frame;
	
	
	private EXGrid grid;

	public WorkflowSearchControl( GridFrame frame, Workflow workflow) {
		super("search", "div");
		this.frame = frame;
		List<State> states = workflow.getStates();
		
		grid = new EXGrid("grid", 2, states.size());
		addChild(grid);
		int count = 0;
		for(State state : states){
			
			EXCheckBox cb = new EXCheckBox(state.getValue().toString());
			Container badge = new EXContainer("badge", "span").addClass("badge").addClass("pull-right").setStyle("cursor", "pointer").setText(state.getLabel()).setStyle("background-color", state.getColor());
			grid.addInCell(0, count	, cb);
			grid.addInCell(1, count, badge);
			count++;
		}
		addClass("search-status");
		
		//<a href="#" style="float:right;margin:5px 10px"><img src='style/icons/tick-button.png'></a>
		
		Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setText("<img src='style/icons/tick-button.png'></img>").setStyle("float", "right").setStyle("margin", "5px 10px").addEvent(this, CLICK);
		addChild(ok);
		
		Container close = new EXContainer("close", "a").setAttribute("href", "#").setText("<img src='style/icons/cross-button.png'></img>").setStyle("float", "right").setStyle("margin", "5px 10px").addEvent(this, CLICK);
		addChild(close);
	}




	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("ok")){
		
			List<BigInteger> status = new ArrayList<BigInteger>(5);
			for(Container tr : grid.getChildren()){
				EXCheckBox cb = tr.getDescendentOfType(EXCheckBox.class);
				if(cb.isChecked()){
					BigInteger i = new BigInteger(cb.getName());
					status.add(i);
				}
			}
			
			frame.search(status);
		
		}else if(container.getName().equals("close")){
			
		}
		getAncestorOfType(WorkflowColumn.class).hide();
		
		
		
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
