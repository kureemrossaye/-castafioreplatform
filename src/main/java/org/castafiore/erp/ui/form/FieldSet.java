package org.castafiore.erp.ui.form;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class FieldSet extends EXContainer{
	
	private List<InputControl<?>> fields = new ArrayList<InputControl<?>>();
	public FieldSet(String name) {
		super(name, "fieldset");
	}

	public void addField(String label, InputControl<?> input){
		
		Container cont = new EXContainer("cont", "div").addClass("w2ui-group");
		addChild(cont);
		Container uilabel = new EXContainer("label", "div").addClass("w2ui-label").setText(label);
		Container uifieldcont = new EXContainer("field", "div").addClass("w2ui-field").addChild(input);
	
		cont.addChild(uilabel);
		cont.addChild(uifieldcont);
		fields.add(input);
		
	}
	
	
	public List<InputControl<?>> getFields(){
		return fields;
	}
	
	
	

}
