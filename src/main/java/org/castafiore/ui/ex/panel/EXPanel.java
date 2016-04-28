/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.ui.ex.panel;

import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXPanel extends EXContainer implements Panel{
	
	
	private Container bodyContainer = new EXContainer("bodyContainer", "div").addClass("panel-body");
	
	private Container widgetHead = new EXContainer("widget-head", "div").addClass("panel-heading");
	
	
	private Container widgetFoot = new EXContainer("widgetFoot", "div").addClass("panel-footer");

	public EXPanel(String name, String title) {
		super(name, "div");
		addClass("panel panel-default");
		addChild(widgetHead);
		widgetHead.setText(title);
		 
		
		addChild(bodyContainer);
		
		addChild(widgetFoot);
		
	}
	
	
	
	
	public EXPanel(String name){
		this(name, "");
	}

	public Container getBody() {
		List<Container> childBody = getBodyContainer().getChildren();
		if(childBody.size() > 0){
			return childBody.get(0);
		}else{
			return null;
		}
	}

	public Panel setBody(Container container) {
		getBodyContainer().getChildren().clear();
		getBodyContainer().setRendered(false);
		getBodyContainer().addChild(container);		
		return this;
	}

	
	public Panel setShowHeader(boolean showHeader) {
		widgetHead.setDisplay(showHeader);
		return this;
		
	}

	public Panel setTitle(String title) {
		widgetHead.setText(title);
			return this;
		
		
	}

	public void addPopup(Container popup){
		addChild(popup);
	}
	
	public Panel setShowFooter(boolean display){
		widgetFoot.setDisplay(display);
		return this;
	}
	
	public Container getFooterContainer(){
		return widgetFoot;
	}
	protected Container getBodyContainer(){
		return bodyContainer;
	}
	
	public Container setDraggable(boolean draggable)
	{
		if(draggable)
		{
			JMap options = new JMap().put("opacity", 0.35).put("handle", "#" +widgetHead.getId());
			options.put("containment", "document");
			setDraggable(true, options);
			setStyle("position", "absolute");
			setStyle("top", "10%");
			setStyle("left", "10%");
		}
		else
		{
			super.setDraggable(false);
			setStyle("position", "static");
		}
		return this;
	}




	@Override
	public Panel setShowCloseButton(boolean b) {
		return this;
	}
	
}
