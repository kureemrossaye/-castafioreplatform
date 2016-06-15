package org.castafiore.erp.ui.dashboard;

import java.util.Map;

import org.castafiore.erp.DashBoard;
import org.castafiore.erp.ui.AbstractEXModule;
import org.castafiore.erp.ui.ERP;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.EXWebServletAwareApplication;

public class EXDashboardModule extends AbstractEXModule implements Event {

	
	
	
	public EXDashboardModule() {
		
		
		
		
		super("Dashboard", "DashBoard", "icon-home", new String[]{"Home"}, "12");
		Container grid = new EXContainer("dashboard","div");
		grid.addClass("dashboard-grid");
		
		DashBoard db = SpringUtil.getBeanOfType(DashBoard.class);
		String scompanyName = db.getLabel();
		Container companyName = new EXContainer("companyName", "h3").setText(scompanyName);
		grid.addChild(companyName);
		for(DashBoard module : db.getChildren()){
			Container shortcut = new EXContainer(module.getName(), "img").setAttribute("src", module.getImg());
			shortcut.setDraggable(true);
			grid.addChild(shortcut);
		}

		Container square = new EXContainer("dashboard", "div").addClass("dashboard");
		square.addChild(grid);
		addChild(square, "0:0");
	}
	
	
	public void onReady(JQuery query){
		
		query.append(
				
		new JQuery(".dashboard td")
			.mouseover(
					//new JQuery(new Var("this"))..addClass("flipInX animated")
					query.clone().eval("$(this).find('ul').css('display','block').addClass('fadeInDown animated')")
					.eval("$(this).find('img').css('transform','scale(1.3)')")
			).mouseout(
					query.clone().eval("$(this).find('ul').css('display','none').removeClass('fadeInDown')")
					.eval("$(this).find('img').css('transform','scale(1)')")
			)
	);
		//query.getDescendentByName("sales").mouseover()
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
