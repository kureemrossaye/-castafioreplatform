package org.castafiore.erp.ui.workflow;

import org.castafiore.ui.ex.EXContainer;

public class EXWorkflowEditor extends EXContainer{

	public EXWorkflowEditor(String name) {
		super(name, "div");
		
		addChild(new JSPlumbWorkflow("js"));
	}

}
