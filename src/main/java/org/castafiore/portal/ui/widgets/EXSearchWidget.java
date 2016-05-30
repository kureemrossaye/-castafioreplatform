package org.castafiore.portal.ui.widgets;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXSearchWidget extends EXContainer {

	private EXInput input = new EXInput("searchField");

	private EXButton submit = new EXButton("submit", "<span class=\"icon-search\"></span>");

	public EXSearchWidget(String name) {
		super(name, "div");
		addClass("searchwidget");

		addChild(new EXContainer("form", "form").setAttribute("action", "#").setAttribute("method", "post"));

		Container inputAppend = new EXContainer("inputAppend", "div").addClass("input-append");

		getChild("form").addChild(inputAppend);

		inputAppend.addChild(input).addChild(submit);

		input.setStyleClass("span2 search-query").setAttribute("placeholder", "Search here...");

	}

}
