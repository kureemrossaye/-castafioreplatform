package org.castafiore.erp.ui.form;

import org.castafiore.ui.ex.EXContainer;

public class ErrorPopup extends EXContainer{

	public ErrorPopup() {
		super("error", "ol");
		//error message ui-corner-all
		addClass("error").addClass("message").addClass("ui-corner-all");
		setDraggable(true);
	}
	
	
	public void addError(String message){
		addChild(new EXContainer("li", "li").setText(message).setStyle("cursor", "pointer"));
	}
	
	public void clear(){
	
		getChildren().clear();
		setRendered(false);
	}
	
	

}
