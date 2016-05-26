package org.castafiore.portal.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXNotificationWidget extends EXContainer {

	private Container dropDownMenu = new EXContainer("dropdownMenu", "ul").addClass("dropdown-menu")
			.addChild(new EXContainer("dropdownheader", "li").addClass("nav-header").setText("Notifications"));

	public EXNotificationWidget(String name) {
		super(name, "div");
		addClass("dropdown").addClass("notification");
		Container dropdownToggle = new EXContainer("dropdown-toggle", "a").setAttribute("data-toggle", "dropdown").addClass("dropdown-toggle")
				.setAttribute("data-target", "#").setAttribute("href", "#")
				.setText("<span class=\"iconsweets-globe iconsweets-white\"></span>");

		addChild(dropdownToggle);

		addChild(dropDownMenu);

	}

}
