package org.castafiore.erp.ui.configs;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.EXMigLayout;

public class ConfigAccordion extends EXMigLayout implements Event{
	
	private Container tabs = new EXContainer("tabs", "ul").addClass("Tabs");
	
	private Container body = new EXContainer("body", "div").addClass("Body");

	public ConfigAccordion(String name) {
		super(name, "12;2:10");
		addClass("ConfigAccordion");
		
		addChild(tabs,"0:1");
		addChild(body, "1:1");
	}
	
	public ConfigAccordion addItem(String label, String icon, Container body){
		
		addTab(label, icon,body);
		
		this.body.addChild(body.setDisplay(false));
		if(this.body.getChildren().size() == 1){
			body.setDisplay(true);
			tabs.getChildByIndex(0).addClass("selected");
		}
		return this;
		
	}
	
	protected void addTab(String label, String icon, Container body){
		
		Container tabItem = new EXContainer("tabItem", "li").addClass("tabItem").setAttribute("cname", body.getName());
		tabItem.addEvent(this, CLICK);
		
		Container uiicon = new EXContainer("uiicon", "img").setAttribute("src", icon).addClass("uiicon");
		tabItem.addChild(uiicon);
		
		Container uilabel = new EXContainer("uilabel", "label").setText(label).addClass("uilabel");
		tabItem.addChild(uilabel);
		
		tabs.addChild(tabItem);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		for(Container c : tabs.getChildren()){
			if(c.equals(container)){
				c.setStyleClass("tabItem selected");
			}else{
				c.setStyleClass("tabItem");
			}
		}
		
		for(Container c : body.getChildren()){
			if(c.getName().equals(container.getAttribute("cname"))){
				if(!c.isVisible()){
					c.setDisplay(true);
				}
			}else{
				if(c.isVisible()){
					c.setDisplay(false);
				}
			}
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
