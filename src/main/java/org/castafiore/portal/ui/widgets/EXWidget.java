package org.castafiore.portal.ui.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXWidget extends EXContainer {

	private Container widgetTitle = new EXContainer("widgetTitle", "h4").addClass("widgettitle");

	private Container widgetContent = new EXContainer("widgetContent", "div");

	public EXWidget(String name, String title) {
		super(name, "div");

		addClass("widget");

		addChild(widgetTitle);

		setTitle(title);

		addChild(widgetContent);

	}

	public EXWidget setTitle(String title) {
		widgetTitle.setText(title);
		return this;
	}

	public EXWidget setBody(Container widgetContent) {
		this.widgetContent.getChildren().clear();
		this.widgetContent.setRendered(false);
		this.widgetContent.addChild(widgetContent);
		return this;
	}
	
	public Container getWidgetContainer(){
		return this.widgetContent;	
	}

}
