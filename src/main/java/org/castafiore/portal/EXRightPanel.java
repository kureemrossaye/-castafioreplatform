package org.castafiore.portal;

import org.castafiore.portal.widgets.EXBreadCrumbWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXRightPanel extends EXContainer {

	private EXHeaderPanel headerPanel = new EXHeaderPanel("headerPanel");

	private EXBreadCrumbWidget breadCrumbWidget = new EXBreadCrumbWidget("breadCrumbWidget");
	
	private Container pageTitle = new EXContainer("pageTitle", "div").addClass("pagetitle");
	
	private EXMainContent mainContent = new EXMainContent("mainContent");

	public EXRightPanel(String name) {
		super(name, "div");

		addClass("rightpanel");

		addChild(headerPanel);

		addChild(breadCrumbWidget);
		
		addChild(pageTitle);
		
		setTitle("Dashboard", "This is a sample description for dashboard page...");
		
		addChild(mainContent);
	}
	
	
	public void setBody(String title, String desc, Container body){
		setTitle(title, desc);
		mainContent.setBody( body);
	}
	
	protected EXRightPanel setTitle(String title, String desc){
		pageTitle.setText("<h1>"+title+"</h1> <span>"+desc+"</span>");
		return this;
	}

}
