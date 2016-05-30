package org.castafiore.portal.ui.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXFormWidget extends EXWidget {
	
	private Container btnsContainer = new EXContainer("btnsContainer", "div").addClass("stdformbutton");

	public EXFormWidget(String name, String title) {
		super(name, title);
	}

	public EXFormWidget addField(String label, StatefullComponent<?> field) {

		Container p = new EXContainer(field.getName() + "_wrapper", "p").addClass("wrapper");

		getWidgetContainer().addChild(p);

		Container uilabel = new EXContainer(field.getName() + "_label", "label").setText(label);
		p.addChild(uilabel);

		Container span = new EXContainer(field.getName() + "_span", "span").addClass("field")
				.addChild(field.addClass("input-xxlarge"));

		p.addChild(span);
		
		addChild(btnsContainer);
		return this;
	}
	
	public EXFormWidget addButton(EXButton btn){
		btnsContainer.addChild(btn);
		return this;
	}
	
	public StatefullComponent getField(String name){
		return (StatefullComponent<Object>)getDescendentByName(name);
	}
	
	
	

}
