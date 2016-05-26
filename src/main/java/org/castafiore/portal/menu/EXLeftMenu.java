package org.castafiore.portal.menu;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXLeftMenu extends EXContainer{

	private Container navHeader = new EXContainer("header", "li").addClass("nav-header");
	
	public EXLeftMenu(String name) {
		super(name, "ul");
		setStyleClass("nav nav-tabs nav-stacked");
		
		addChild(navHeader);
	}
	
	public EXLeftMenu setTitle(String title){
		navHeader.setText(title);
		return this;
	}
	
	public EXLeftMenu addMenuItem(EXMenuItem item){
		addChild(item);
		return this;
	}

}
