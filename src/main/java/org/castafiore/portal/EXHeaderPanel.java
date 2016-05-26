package org.castafiore.portal;

import org.castafiore.portal.widgets.EXNotificationWidget;
import org.castafiore.portal.widgets.EXUserInfoWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXHeaderPanel extends EXContainer {

	private Container showMenu = new EXContainer("showMenu", "a").setAttribute("href", "#").addClass("showmenu");

	private EXNotificationWidget notifications = new EXNotificationWidget("notifications");

	private EXUserInfoWidget userInfoWidget = new EXUserInfoWidget("userInfo");

	public EXHeaderPanel(String name) {
		super(name, "div");

		addClass("headerpanel");
		addChild(showMenu);

		Container headerRight = new EXContainer("headerRight", "div").addClass("headerright");
		
		addChild(headerRight);
		
		headerRight.addChild(notifications);
		
		headerRight.addChild(userInfoWidget);
	}

}
