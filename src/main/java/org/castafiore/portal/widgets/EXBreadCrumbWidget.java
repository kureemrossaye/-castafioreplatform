package org.castafiore.portal.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXBreadCrumbWidget extends EXContainer{

	private Container breadcrumb = new EXContainer("breadcrumb", "ul").addClass("breadcrumb");
	
	public EXBreadCrumbWidget(String name) {
		super(name, "div");
		
		addClass("breadcrumbwidget");
		
		addChild(breadcrumb);
		
		addLink("Home").addLink("Dashboard");
		
	}
	
	public EXBreadCrumbWidget addLink(String label){
		Container li = new EXContainer("", "li");
		if(breadcrumb.getChildren().size() > 0){
			breadcrumb.getChildByIndex(breadcrumb.getChildren().size() -1).addChild(new EXContainer("div", "span").addClass("divider").setText("/")).removeClass("active");
			
		}
		
		breadcrumb.addChild(li.addClass("active").addChild(new EXContainer("", "a").setText(label).setAttribute("href", "#")));
		
		return this;
	}
	
	
	
	

}
