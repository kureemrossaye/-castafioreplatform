package org.castafiore.erp.ui.form;

import java.math.BigInteger;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.ActionScope;
import org.castafiore.erp.reference.ReferenceManager;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;

public class CodeFieldAction implements FieldAction, Event{

	private InputControl field;
	
	private IGroup group;
	
	private Class<? extends BaseErpModel> vo;
	
	@Override
	public EXButtonSet getButtons(InputControl<?> field, IGroup group, Class<? extends BaseErpModel> vo) {
		this.field = field;
		this.group = group;
		this.vo = vo;
		EXIconButton btn = new EXIconButton("activate","activate" );
		btn.setAttribute("title", "Lock this code. Once locked, this code cannot be used again.");
		btn.addEvent(this, CLICK);
		
		EXButtonSet set = new EXButtonSet("sa");
		btn.setStyle("padding", "8px");
		set.addItem(btn);
		return set;
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		BigInteger defaultStatus = group.getAncestorOfType(Forms.class).getDefaultStatus();
		Object val =ReferenceManager.lockNextCode(vo,group,defaultStatus).toString();
		field.setValue(val);
		field.setEnabled(false);
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	@Override
	public void changeMode(EXButtonSet buttons, ActionScope scope,
			InputControl<?> field, IGroup group,
			Class<? extends BaseErpModel> vo) {
		// TODO Auto-generated method stub
		
	}

}
