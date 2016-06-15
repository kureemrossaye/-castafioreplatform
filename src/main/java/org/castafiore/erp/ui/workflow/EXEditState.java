package org.castafiore.erp.ui.workflow;

import java.awt.Color;
import java.math.BigInteger;
import java.util.Map;

import org.castafiore.erp.ui.form.controls.IntegerControl;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXEditState extends EXContainer implements Event{
	
	private EXInput label = new EXInput("label");
	
	private IntegerControl stateValue = new IntegerControl("stateValue");
	
	private EXButton ok = new EXButton("ok", "Ok");
	
	private EXButton cancel = new EXButton("cancel", "Cancel");
	
	private EXColorPicker color = new EXColorPicker("color", Color.CYAN);
	
	private EXState state;

	public EXEditState(String name, EXState state) {
		super(name, "div");
		
		Container label = new EXContainer("lblLabel", "label").setText("Label :");
		addChild(label);
		addChild(this.label);
		this.label .setValue(state.getState().getLabel());
		
		addChild(new EXContainer("sa", "label").setText("State value :"));
		addChild(stateValue);
		stateValue.setValue(state.getState().getValue());
		
		addChild(new EXContainer("sa", "label").setText("Color :"));
		addChild(color);
		color.setAttribute("value", state.getState().getColor()).addClass("form-control").setStyle("height", "20px");
		addChild(ok.addEvent(this, CLICK));
		
		addChild(cancel.addEvent(this, CLICK));
		
		
		
		this.state = state;
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("ok")){
			SpringUtil.getBeanOfType(Dao.class).getSession().load(state.getState(), state.getState().getId());
			state.getState().setLabel(label.getValue());
			state.getState().setValue(stateValue.getValue());
			state.getState().setColor(color.getAttribute("value"));
			state.setStyle("background-color", color.getAttribute("value"));
			SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(state.getState());
			
		}
		
		state.setLabel(state.getState().getLabel());
		
		setDisplay(false);
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
