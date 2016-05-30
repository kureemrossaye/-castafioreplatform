package org.castafiore.portal.ui.menu;

import java.util.Map;

import org.castafiore.portal.model.NavigationNode;
import org.castafiore.portal.ui.EXPortal;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXMenuItem extends EXContainer implements Event{

	private NavigationNode node_;
	public EXMenuItem(NavigationNode node) {
		super(node.getName(), "li");
		this.node_ = node;
		addChild(new EXContainer("a", "a").setAttribute("href", "#"));
		setContent(node.getLabel(), node.getIconClass());
		addEvent(this, CLICK);
	}

	public EXMenuItem setContent(String title, String icon) {
		getChild("a").setText("<span class=\"" + icon + "\"></span>" + title );
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

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		
		getAncestorOfType(EXPortal.class).redirectTo(node_);
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request) throws UIException {
		
	}

}
