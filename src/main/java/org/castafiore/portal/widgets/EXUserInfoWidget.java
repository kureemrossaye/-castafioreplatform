package org.castafiore.portal.widgets;

import org.castafiore.security.model.User;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXUserInfoWidget extends EXContainer {

	
	private Container dropDownMenu = new EXContainer("dropdownMenu", "ul").addClass("dropdown-menu");
	
	public EXUserInfoWidget(String name) {
		super(name, "div");
		addClass("dropdown").addClass("userinfo");
		Container dropdownToggle = new EXContainer("dropdown-toggle", "a").setAttribute("data-toggle", "dropdown").addClass("dropdown-toggle")
				.setAttribute("data-target", "#").setAttribute("href", "#").setText("Hi, ThemePixels! <b class=\"caret\"></b>");

		addChild(dropdownToggle);
		
		addChild(dropDownMenu);
		
		addMenuItem("icon-edit", "Edit Profile");
		
		addMenuItem("icon-wrench", "Account Settings");
		
		addMenuItem("icon-eye-open", "Privacy Settings");
		
		addDivider();
		
		addMenuItem("icon-off", "Sign Out");
	}
	
	
	public EXUserInfoWidget setUser(User user){
		
		getChild("dropdown-toggle").setText("Hi, "+user.getFirstName() + " " + user.getLastName()+"! <b class=\"caret\"></b>");
		return this;
	}
	
	protected void addMenuItem(String icon, String text){
		dropDownMenu.addChild(new EXContainer("", "li").setText("<a href=\"#\"><span class=\""+icon+"\"></span> "+text+"</a>"));
	}
	
	protected void addDivider(){
		dropDownMenu.addChild(new EXContainer("", "li").addClass("divider"));
	}

}
