package org.castafiore.erp.ui.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Process;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;

public class ProcessButton extends EXButton implements Event{
	
	private Process process;
	
	private BaseErpModel data;
	
	private GridController controller_;
	
	private List<ProcessHandler> handlers = new LinkedList<ProcessButton.ProcessHandler>();

	public ProcessButton(Process act, BaseErpModel data, GridController controller) {
		super(act.getId() + "", act.getLabel());
		addEvent(this, CLICK);
		this.process =act;
		this.data = data;
		setSize(EXButton.SIZE_EXTRA_SMALL);
		if(controller != null){
			controller_ = controller;
		}else{
			controller_ = new GridController();
		}
		setStyle("background", process.getTarget().getColor());
	}
	
	
	public ProcessButton addProcessHandler(ProcessHandler handler){
		this.handlers.add(handler);
		return this;
	}
	
	public void fireHandlers(boolean pre){
		for(ProcessHandler h : handlers){
			if(pre){
				h.preProcess(process, data);
			}else{
				h.postProcess(process, data);
			}
		}
	}

	@Override
	public void ClientAction(JQuery container) {
		
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		WorkflowManager manager = SpringUtil.getBeanOfType(WorkflowManager.class);
		controller_.activate(process);
		controller_.activate(data);
		try{
			fireHandlers(true);
			manager.process(process, data);
			fireHandlers(false);
		
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}
	
	

	public interface ProcessHandler{
		public void preProcess(Process process, BaseErpModel data);
		
		public void postProcess(Process process, BaseErpModel model);
	}
	
}
