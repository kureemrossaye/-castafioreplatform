package org.castafiore.erp.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXMenu extends EXContainer implements Event{

	public EXMenu(String name) {
		super(name, "ul");
		addClass("sidenav");
	}
	
	
	public EXMenuItem addLeafNode(String name, String label, String icon){
		EXMenuItem item = new EXMenuItem(name, label, icon, false);
		addChild(new EXContainer("li", "li").addChild(item));
		return item;
	}
	
	public EXMenuItem addNode(String name, String label, String icon, EXMenu children){
		children.removeClass("sidenav");
		EXMenuItem item = new EXMenuItem(name, label, icon, true);
		item.addEvent(this, CLICK);
		addChild(new EXContainer("li", "li").addClass("has_sub").addChild(item).addChild(children));
		return item;
	}
	
	
	public class EXMenuItem extends EXContainer{

		public EXMenuItem(String name, String label, String icon, boolean hasChildren) {
			super(name, "a");
			
			setAttribute("href", "#");
			
			//<a href="#"><i class="icon-list-alt"></i> Widgets  <span class="pull-right"><i class="icon-chevron-right"></i></span></a>
			
			String txt = "<i class=\""+icon+"\"></i>" + label ;
			
			if(hasChildren){
				txt = txt + "<span class=\"pull-right\"><i class=\"icon-chevron-right\"></i></span>";
			}
			
			//txt = txt + "</a>";
			
			setText(txt);
		}
		
	}


	@Override
	public void ClientAction(JQuery container) {
		
		container.eval(" if($(this).parent().hasClass(\"has_sub\")) { event.preventDefault();}   ")
		
		.IF(container.clone().getAttribute("class").equal("subdrop"), container.clone().removeClass("subdrop").getParent().getChildren().get(1).slideUp(350), 
				
				container.clone()
				.append(new JQuery(".sidenav li ul").slideUp(350))
				.append(new JQuery(".sidenav li a").removeClass("subdrop"))
				
				.getParent().getChildren().get(1).slideDown(350).append(container.clone().addClass("subdrop"))
			);
		
		
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
