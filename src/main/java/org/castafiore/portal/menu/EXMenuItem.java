package org.castafiore.portal.menu;

import org.castafiore.ui.ex.EXContainer;

public class EXMenuItem extends EXContainer {

	public EXMenuItem(String name, String title, String icon) {
		super(name, "li");
		addChild(new EXContainer("a", "a").setAttribute("href", "#"));
	}

	public EXMenuItem setContent(String title, String icon) {
		getChild("a").setText("<span class=\"" + icon + "\"></span>" + title + "</a>");
		return this;
	}
	
	public EXMenuItem addSubMenu(String title){
		if(getChildren().size() == 1){
			addChild(new EXContainer("sub", "ul"));
			addClass("dropdown");
		}
		
		getChildByIndex(1).addChild(new EXContainer("", "a").setAttribute("href", "#").setText(title));
		return this;
	}

}
