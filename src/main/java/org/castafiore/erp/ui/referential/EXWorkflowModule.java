package org.castafiore.erp.ui.referential;

import org.castafiore.erp.Action;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.workflow.EXWorkflowEditor;
import org.castafiore.ui.Container;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class EXWorkflowModule extends AbstractEXModule{

	public EXWorkflowModule() {
		super("EXWorkflowModule", "Workflow module", "", new String[]{}, "12");
		
		
//		final GridFrame frame = new GridFrame(Workflow.class);
//		final GridFrame actions = new GridFrame(Action.class);
//		EXTabPanel tabs = new EXTabPanel("tabs",new TabModel() {
//			
//			@Override
//			public int size() {
//				return 2;
//			}
//			
//			@Override
//			public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
//				if(index ==0){
//					return "Workflow";
//				}else{
//					return "Actions";
//				}
//			}
//			
//			@Override
//			public Container getTabContentAt(TabPanel pane, int index) {
//				if(index == 0)
//					return frame;
//				else
//					return actions;
//
//			}
//			
//			@Override
//			public int getSelectedTab() {
//				return 0;
//			}
//		});
		
		
		EXWorkflowEditor tabs = new EXWorkflowEditor("sad");
		
		addChild(tabs, "0:0");
		
	}

}
