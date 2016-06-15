package org.castafiore.erp.ui;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ui.conference.EXConferenceDashBoard;
import org.castafiore.erp.ui.dashboard.EXMainDashboardModule;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.erp.ui.referential.EXDashBoardModule;
import org.castafiore.erp.ui.referential.EXNewDashBoardModule;
import org.castafiore.erp.ui.referential.EXQuickEditModule;
import org.castafiore.erp.ui.view.ViewAssociation;
import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class EXModuleContainer extends EXContainer{
	
	private AbstractEXModule currentModule = null;
	
	//private Container labelContainer = new EXContainer("labelContainer", "h2").addClass("pull-left");
	
	
	//private Container breadCrumbDynamics =new EXContainer("dynamics", "span");
	
	//this is where the actual module resides
	private Container matter = new EXContainer("matter", "div").setStyle("margin", "0px").setStyle("border", "none");
	
	private  Container pageHead = new PageHead("pageHead", "ul").addClass("nav nav-pills").setStyle("margin-top", "0px").setStyle("padding", "3px");

	public EXModuleContainer() {
		super("EXModuleContainer", "div");
		addClass("");
		
		 addChild(pageHead);
		 setStyle("margin-left", "0px");
		 
		 addChild(matter);
		 try{
			// setCurrentModule(EXConferenceDashBoard.class);
			 setCurrentModule(EXMainDashboardModule.class);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
	}
	
	
	public void quickEdit(BaseErpModel model){
		if(currentModule != null){
			currentModule.setDisplay(false);
		}
		currentModule = new EXQuickEditModule(model);
		currentModule.setName(System.currentTimeMillis() + "");
		matter.addChild(currentModule.setStyle("margin", "0px").setStyle("padding", "0"));
		addTab();
		selectTab();
		currentModule.setDisplay(true);
	}
	
	
	public void setCurrentModule(Class<? extends AbstractEXModule> moduleClazz)throws Exception{
		
		
		if(currentModule != null){
			currentModule.setDisplay(false);
		}
		this.currentModule = matter.getDescendentOfType(moduleClazz);
		if(currentModule == null){
			currentModule = moduleClazz.newInstance();
			currentModule.setName(moduleClazz.getSimpleName());
			matter.addChild(currentModule.setStyle("margin", "0px").setStyle("padding", "0"));
			addTab();
		}
		selectTab();
		currentModule.setDisplay(true);
	}
	
	public void setCurrentModule(String name){
		if(currentModule != null){
			currentModule.setDisplay(false);
		}
		this.currentModule = (AbstractEXModule)matter.getChild(name);
		selectTab();
		currentModule.setDisplay(true);
	}
	
	private void addTab(){
		String label = currentModule.getLabel();
		Container li = new EXContainer(currentModule.getName(), "li");
		
		Container a = new EXContainer("", "a").setAttribute("href", "#" + currentModule.getName());
		
		a.addChild(new EXContainer("", "span").setText(label));
		Container remove = new EXContainer("remove", "a").setText("x").setAttribute("class", "close");
		a.addChild(remove);
		li.addChild(a);
		li.addClass("active");
		
		a.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				if(!container.getName().equals("remove")){
					Class c = matter.getChild(container.getParent().getName()).getClass();
					Class zz = EXQuickEditModule.class;
					if(c.equals(zz)){
						String name = container.getParent().getName();
						setCurrentModule(name);
					}else{
						try{
							setCurrentModule(c);
						}catch(Exception e){
							//throw new UIException(e);
						}
					}
				}
				return true;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				
//				container.append(new JQuery( container.getParent().getIdRef() + " li.active").removeClass("active"));
//				container.append(new JQuery("#" + currentModule.getId()).fadeOut(100));
//				container.append(new JQuery("#" + matter.getChild(container.getAttribute("name").getServerData())).fadeIn(100));
				
				
				container.server(this);
				
			}
		}, Event.CLICK);
		
		remove.addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				
				if(request.containsKey("toremove")){
					container.append(new JQuery("#" + request.get("toremove")).hide(100));
				}
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				String name = container.getParent().getParent().getName();
				request.put("toremove", matter.getChild(name).getId());
				Container c = matter.getChild(name);
				matter.getChildren().remove(c);
				container.remove();
				return false;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				container.getParent().getParent().fadeOut(100);
				container.mask().server(this);
			}
		}, Event.CLICK);
		//li.addChild(a);
		
		pageHead.addChild(li);
		
	}
	
	private void selectTab(){
		String name = currentModule.getName();
		for(Container c : pageHead.getChildren()){
			
			if(c.getName().equals(name)){
				if(!c.getStyleClass().contains("active")){
					c.addClass("active");
				}
			}else{
				if(c.getStyleClass().contains("active")){
					c.removeClass("active");
				}
			}
		}
	}
	
	
	public class PageHead extends EXContainer implements Droppable, Event{

		public PageHead(String name, String tagName) {
			super(name, tagName);
			addEvent(this, DND_DROP);
			
			// TODO Auto-generated constructor stub
		}

		@Override
		public String[] getAcceptClasses() {
			return new String[]{"view-association"};
		}

		@Override
		public JMap getDroppableOptions() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void ClientAction(JQuery container) {
			container.makeServerRequest(new JMap().put("sourceid", JQuery.getDragSourceId()), this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			ViewAssociation ass = (ViewAssociation) container.getAncestorOfType(EXModuleContainer.class).getDescendentById(request.get("sourceid"));
			
			BaseErpModel model = ass.getValue();
			GridController controller = new GridController();
			controller.activate(model);
			
			container.getAncestorOfType(EXModuleContainer.class).quickEdit(model);
			return true;
		}

		@Override
		public void Success(JQuery container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	

}
