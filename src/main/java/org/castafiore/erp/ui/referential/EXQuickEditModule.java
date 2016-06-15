package org.castafiore.erp.ui.referential;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.form.Forms;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.button.EXButtonSet;

public class EXQuickEditModule extends AbstractEXModule implements Event{

	public EXQuickEditModule(BaseErpModel model) {
		super("EXQuickEdit", "Fast edit", "", new String[]{}, "12");
		
		
		
		//EXWorkflowEditor tabs = new EXWorkflowEditor("sad");
		
		EXButtonSet set = new EXButtonSet("set");
		set.addClass("ui-state-active").setStyle("width", "100%").setStyle("padding", "5px");
		EXButton save = new EXButton("save", "Save");
		save.setStyleClass("btn btn-xs");
		set.addItem(save);
		addChild(set,"0:0");
		save.addEvent(this, CLICK);
		org.castafiore.erp.annotations.Forms aaforms = ReflectionUtils.getAnnotation(model.getClass(), org.castafiore.erp.annotations.Forms.class);
				
		Forms forms =new Forms(aaforms,model.getClass(), new GridController()); 
		addClass("w2ui-page");
		
		addChild(forms, "0:0");
		model = new GridController().activate(model);
		forms.setData(model);
		
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
		getDescendentOfType(Forms.class).saveOrUpdate();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
