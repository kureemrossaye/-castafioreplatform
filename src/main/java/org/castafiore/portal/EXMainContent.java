package org.castafiore.portal;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXMainContent extends EXContainer {

	private Container contentInner = new EXContainer("contentInner", "div").addClass("contentinner");

	public EXMainContent(String name) {
		super(name, "div");
		addClass("maincontent");
		addChild(contentInner);

	}
	
	public EXMainContent setBody(Container body){
		contentInner.getChildren().clear();
		contentInner.setRendered(false);
		contentInner.addChild(body);
		return this;
	}

}
