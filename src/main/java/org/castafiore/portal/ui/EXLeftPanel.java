package org.castafiore.portal.ui;

import java.util.Date;

import org.castafiore.portal.model.NavigationNode;
import org.castafiore.portal.model.Portal;
import org.castafiore.portal.ui.menu.EXLeftMenu;
import org.castafiore.portal.ui.menu.EXMenuItem;
import org.castafiore.portal.ui.widgets.EXDateWidget;
import org.castafiore.portal.ui.widgets.EXPlainWidget;
import org.castafiore.portal.ui.widgets.EXSearchWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXLeftPanel extends EXContainer {

	private Container logoPanel = new EXContainer("logoPanel", "div").addClass("logopanel");

	private EXPlainWidget plainWidget = new EXPlainWidget("plainWidget");

	private EXSearchWidget searchWidget = new EXSearchWidget("searchWidget");

	private EXDateWidget dateWidget = new EXDateWidget("dateWidget");

	private EXLeftMenu leftMenu = new EXLeftMenu("leftMenu");

	public EXLeftPanel(String name) {
		super(name, "div");
		addClass("leftpanel");

		
		initLeftPanel();
	}

	private void initLogoPanel() {
		addChild(logoPanel);
		logoPanel.addChild(new EXContainer("h", "h1").addChild(
				new EXContainer("appName", "a").setAttribute("href", "#").setText("Castafiore Platform <span>v1.0</span>")));
	}

	private void initLeftPanel() {

		initLogoPanel();
		addChild(dateWidget.setDate(new Date()));
		addChild(searchWidget);
		addChild(plainWidget);
		plainWidget.setBottomLabel("30% full").setTopLabel("Using 16.8 GB of your 51.7 GB").setProgressValue(38);
		addChild(new EXContainer("leftMenuWrapper", "div").addClass("leftmenu").addChild(leftMenu));
	}
	
	public void setPortal(Portal portal){
		leftMenu.setTitle(portal.getNavigation().getLabel());
		for(NavigationNode node : portal.getNavigation().getChildren()){
			addMenuItem(node);
		}
	}

	public void addMenuItem(NavigationNode node) {
		leftMenu.addMenuItem(new EXMenuItem(node));
	}

}
