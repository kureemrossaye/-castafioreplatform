package org.castafiore.erp.ui.referential;

import java.util.Map;
import java.util.Random;

import org.castafiore.erp.DashBoard;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.ERP;
import org.castafiore.erp.utils.CSSUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXWebServletAwareApplication;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXNewDashBoardModule extends AbstractEXModule implements Event{
	
	
	private Container columns = new EXContainer("columns", "div").addClass("pricing-inner");
	
	private final static Random rand = new Random();

	public EXNewDashBoardModule() {
		
		super("Dashboard", "DashBoard", "icon-home", new String[]{"Home"}, "12");
		
		EXXHTMLFragment fragment = new EXXHTMLFragment("fragment", "templates/EXDashBoard.xhtml");
		
	
		
		
		
		DashBoard db = SpringUtil.getBeanOfType(DashBoard.class);
		String scompanyName = db.getLabel();
		
		
		Container companyName = new EXContainer("title", "h1").setText(scompanyName).addClass("btn").addEvent(this, CLICK).setAttribute("module", db.getModule());
		fragment.addChild(companyName);
		fragment.addChild(columns);
		
		for(DashBoard module : db.getChildren()){
			
			Container column = new EXContainer("c", "div").addClass("column");
			columns.addChild(column);
			Container priceHead = new EXContainer("priceHead", "div").addClass("price-head");
			column.addChild(priceHead);
			Container moduleTitle  = new EXContainer("ttl", "h2").addClass("title").setText(module.getLabel());
			priceHead.addChild(moduleTitle);
			
			Container priceCOntent = new EXContainer("priceContent", "div").addClass("price-content");
			column.addChild(priceCOntent);
			
			
			if(module.getChildren().size() > 0){
				Container options = new EXContainer("options", "ul");
				priceCOntent.addChild(options);
				
				for(DashBoard child : module.getChildren()){
					Container strong = new EXContainer(child.getName(), "strong").addClass(CSSUtil.HOVERS[rand.nextInt(CSSUtil.HOVERS.length)]).setText(child.getLabel()).addEvent(this, CLICK).setAttribute("module", child.getModule());
					Container help = new EXContainer(child.getName() + "Help", "p").setText(child.getHelp());
					options.addChild(new EXContainer("", "li").addChild(strong).addChild(help));
					
				}
			}
		}

		addChild(fragment, "0:0");
		
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
				ERP app = getAncestorOfType(ERP.class);
				app.getChildren().clear();
				app.setRendered(false);
				//getAncestorOfType(ERP.class).setRendered(false);
				app.addChild(new EXContainer("", "h1").setText("Please log before accessing this application"));
				app.addChild(new EXContainer("", "a").setAttribute("href", "logout.jsp").setText("Please click here to log in"));
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
