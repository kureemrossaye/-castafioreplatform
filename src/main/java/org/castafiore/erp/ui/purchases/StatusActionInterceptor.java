package org.castafiore.erp.ui.purchases;

import java.math.BigInteger;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Process;
import org.castafiore.erp.PurchasesOrder;

import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.ui.form.AbstractActionInterceptor;
import org.castafiore.erp.ui.form.Forms;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.workflow.ProcessButton;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class StatusActionInterceptor extends AbstractActionInterceptor<PurchasesOrder>{
	
	private BigInteger status;
	public StatusActionInterceptor(BigInteger status){
		this.status = status;
	}
	
	public BigInteger getStatus(){
		return status;
	}

	@Override
	public void preSave(BaseErpModel instance, Forms forms) {
		instance.setStatus(status);
		super.preSave(instance, forms);
	}

	@Override
	public void preUpdate(BaseErpModel instance, Forms forms) {
		instance.setStatus(status);
		super.preUpdate(instance, forms);
	}
	
	

	@Override
	public void postSetData(BaseErpModel data, final Forms forms) {
		BigInteger defaultStatus = forms.getDefaultStatus();
		if(data.getId() != null && data.getStatus() != null){
			defaultStatus = data.getStatus();
		}
		if(defaultStatus != null){
			EXToolBar formToolbar = forms.getAncestorOfType(GridFrame.class).getFormToolbar();
			EXButton addNewButton =  forms.getAncestorOfType(GridFrame.class).getAddNewButton();
			State state = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(data.getClass()).getState(defaultStatus);
			addNewButton.setText("Add new " + state.getLabel());
			Container container = formToolbar.getChild("workflowset");
			EXButtonSet set = null;
			if(container != null){
				set = (EXButtonSet)container;
				if(data.getId() == null){
					set.setDisplay(false);
				}else{
					addButtons(set, data, defaultStatus, forms);
				}
			}else{
				if(data.getId() != null){
					set = new EXButtonSet("workflowset");
					formToolbar.addItem(set); 
					addButtons(set, data, defaultStatus, forms);
				}
			}
		
			
			forms.setTabLabel(state.getLabel(), 0);
		
		}
		
		super.postSetData(data, forms);
	}
	public void addButtons(EXButtonSet set, BaseErpModel data, BigInteger defaultStatus, final Forms forms){
		set.getChildren().clear();
		set.setRendered(false);
		set.setStyle("display", "inline-block");
		Workflow workflow = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(data.getClass());
		if(workflow != null){
			
			State state = workflow.getState(defaultStatus);
			if(state != null){
				for(Process p : state.getActions()){
					ProcessButton btn = new ProcessButton(p, data, forms.getController());
					btn.addProcessHandler(new ProcessButton.ProcessHandler() {
						
						@Override
						public void preProcess(Process process, BaseErpModel data) {
							
							
						}
						
						@Override
						public void postProcess(Process process, BaseErpModel model) {
							forms.setData(model, true);
							
						}
					});
					set.addItem(btn);
				}
			}
		}
	}
	
	

}
