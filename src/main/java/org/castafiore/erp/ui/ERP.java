package org.castafiore.erp.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;

public class ERP extends EXWebServletAwareApplication implements Event{
	
	
	//private EXMenu menu = null;
	
	private EXModuleContainer moduleContainer = new EXModuleContainer();
	
	private Container tabs = new EXContainer("tabs", "ul").addClass("nav nav-pills");
	

	public ERP() {
		super("erp");
		
		init();
		
	
	}
	
	
	public EXModuleContainer getModuleContainer(){
		return moduleContainer;
	}
	
	
	private void openModule(String name){
		
	}
	
	private void init(){
		addClass("content");
		addEvent(this, MISC);
		
		addChild(moduleContainer);
		
	}
	
	

	@Override
	public void ClientAction(JQuery container) {
		container.mask().server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			
			if( getRequest().getSession().getAttribute("credential") == null){
				//this.getChildren().clear();
			//	addChild(new EXContainer("", "h1").setText("Please log before accessing this application"));
				//addChild(new EXContainer("", "a").setAttribute("href", "logout.jsp").setText("Please click here to log in"));
				getRequest().getSession().setAttribute("credential", "system");
			}else{
			
				Class clazz =  Thread.currentThread().getContextClassLoader().loadClass(container.getAttribute("clazz")); //CLAZZ[Integer.parseInt(container.getAttribute("index"))];
				moduleContainer.setCurrentModule(clazz);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

	

}
