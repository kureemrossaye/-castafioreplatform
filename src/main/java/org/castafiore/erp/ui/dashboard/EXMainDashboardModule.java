package org.castafiore.erp.ui.dashboard;

import java.util.Map;
import java.util.Random;

import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.ERP;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXMainDashboardModule extends AbstractEXModule implements Event{
	
	private final static String[] NAMES = { "Inventory", "Sales", "Purchases"};
	
	private final static Class[] clazz = {EXInventoryDashBoard.class, EXSalesDashBoard.class, EXPurchasesDashBoard.class};
	
	private final static String[] STYLES ={"","", "position:relative;top:-30px"};

	public EXMainDashboardModule() {
		super("Dashboard", "Home");
		init();
		Random r = null;
	}
	
	
	private void init(){
		EXXHTMLFragment fragment = new EXXHTMLFragment("s","templates/EXMainDashBoard.xhtml");
		addChild(fragment, "0:0");
		for(int i =0; i < NAMES.length; i++){
			String name = NAMES[i];
			Container img = new EXContainer(name, "img").setAttribute("src", "Workflow_files/"+name+".png").setAttribute("width", "128").setAttribute("height", "128");
			img.addEvent(this, CLICK);
			if(StringUtil.isNotEmpty(STYLES[i])){
				img.setAttribute("style", STYLES[i]);
			}
			img.addClass("grow-rotate");
			img.setAttribute("module", clazz[i].getName());
			fragment.addChild(img);
		}
	}

	
	
	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		try{ 
			
			if( ((EXWebServletAwareApplication)getRoot()).getRequest().getSession().getAttribute("credential") == null){
//				ERP app = getAncestorOfType(ERP.class);
//				app.getChildren().clear();
//				app.setRendered(false);
//				//getAncestorOfType(ERP.class).setRendered(false);
//				app.addChild(new EXContainer("", "h1").setText("Please log before accessing this application"));
//				app.addChild(new EXContainer("", "a").setAttribute("href", "logout.jsp").setText("Please click here to log in"));
				((EXWebServletAwareApplication)getRoot()).getRequest().getSession().setAttribute("credential", "system");
				Class c = Class.forName(container.getAttribute("module"));
				
				getAncestorOfType(ERP.class).getModuleContainer().setCurrentModule(c);
			}else{
				Class c = Class.forName(container.getAttribute("module"));
			
				getAncestorOfType(ERP.class).getModuleContainer().setCurrentModule(c);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
