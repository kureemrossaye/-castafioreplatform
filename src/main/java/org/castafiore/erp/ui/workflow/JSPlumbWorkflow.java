package org.castafiore.erp.ui.workflow;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.KeyValuePair;
import org.castafiore.SimpleKeyValuePair;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.Process;
import org.castafiore.erp.State;
import org.castafiore.erp.Workflow;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.erp.workflow.WorkflowManager;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.js.Function;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;

public class JSPlumbWorkflow extends EXXHTMLFragment implements Event, PopupContainer{
	
	private EXButton addState = new EXButton("addState", "Add new state");
	
	private EXOverlayPopupPlaceHolder overlay = new EXOverlayPopupPlaceHolder("overlay");
	
	private Container statesContainer = new EXContainer("states", "div");
	
	private EXSelect<KeyValuePair> entities;
	
	private Class<? extends BaseErpModel> vo;

	public JSPlumbWorkflow(String name) {
		super(name, "templates/JSPlumbWorkflow.xhtml");
		
		Map<String, ClassMetadata> metadata = SpringUtil.getBeanOfType(SessionFactory.class).getAllClassMetadata();
		
		List<KeyValuePair> kvs = new ArrayList<KeyValuePair>(metadata.size());
		
		for(String key : metadata.keySet()){
			try{
				Class<?> cc = metadata.get(key).getMappedClass();
				System.out.println(cc);
				//ClassMetadata meta = metadata.get(key);
				
				if(ReflectionUtils.getAnnotation(cc,org.castafiore.erp.annotations.Workflow.class) != null){
					String value = cc.getSimpleName();
					String ent = key;
					kvs.add(new SimpleKeyValuePair(ent,value));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		DefaultDataModel<KeyValuePair> model = new DefaultDataModel<KeyValuePair>(kvs);
		
		entities = new EXSelect<KeyValuePair>("entities", model);
		
		addChild(entities);
		
		entities.addEvent(this, CHANGE);
		
		addChild(statesContainer);
		
		addChild(addState.addEvent(this, CLICK));
		
		addChild(overlay);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this).eval("instance.destroy()");
		
	}
	
	
	public void openWorkflow(Class<? extends BaseErpModel> vo){
		this.vo = vo;
		
		statesContainer.getChildren().clear();
		statesContainer.setRendered(false);
		
		Workflow workflow = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(vo);
	
		if(workflow == null){
			workflow = new Workflow();
			workflow.setEntity(vo.getName());
			SpringUtil.getBeanOfType(Dao.class).getSession().save(workflow);
		}
		
		if(workflow != null){
			List<State> states = workflow.getStates();
			
			for(State state : states){
				statesContainer.addChild(new EXState(state));
			}
		}
		
	}
	
	
	public State createNewState(){
		Workflow workflow = SpringUtil.getBeanOfType(WorkflowManager.class).getWorkflow(vo);
		
		int max = 0;
		
		for(State st : workflow.getStates()){
			BigInteger val = st.getValue();
			if(val != null && val.intValue() > max){
				max = val.intValue();
			}
		}
		
		State state = new State();
		state.setColor("Beige");
		state.setLabel("New state");
		state.setValue(new BigInteger((max +1) + ""));
		Dao dao = SpringUtil.getBeanOfType(Dao.class);
		dao.getSession().saveOrUpdate(state);
		workflow.getStates().add(state);
		dao.getSession().update(workflow);
		EXState uistate = new EXState(state);
		statesContainer.addChild(uistate);
		return state;
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("clicksource") && request.containsKey("clicktarget")){
			String sourceId = request.get("clicksource").replace("\"", "").trim();
			String targetId = request.get("clicktarget").replace("\"", "").trim();
			Dao dao = SpringUtil.getBeanOfType(Dao.class);
			State source = ((EXState)statesContainer.getDescendentById(sourceId)).getState();
			
			State target = ((EXState)statesContainer.getDescendentById(targetId)).getState();
			dao.getSession().load(source, source.getId());
			dao.getSession().load(target, target.getId());
			
			for(Process p : source.getActions()){
				if(p.getTarget().getId() == target.getId()){
					p.setLabel(request.get("label"));
					dao.getSession().update(p);
				}
			}
			return true;
		}
		
		if(request.containsKey("connectionclick")){
			System.out.println(request);
		}
		
		if(request.containsKey("source") && request.containsKey("target") ){
			
			String sourceId = request.get("source").replace("\"", "").trim();
			String targetId = request.get("target").replace("\"", "").trim();
			
			Dao dao = SpringUtil.getBeanOfType(Dao.class);
			 
			State source = ((EXState)statesContainer.getDescendentById(sourceId)).getState();
			
			State target = ((EXState)statesContainer.getDescendentById(targetId)).getState();
			dao.getSession().load(source, source.getId());
			dao.getSession().load(target, target.getId());
			if(!source.isConnected(target)){
			
				
				
				Process process = source.connect(target);
				dao.getSession().save(process);
				
				dao.getSession().save(source);
				dao.getSession().save(target);
			}
			
			return true;
		}
		
		if(container instanceof EXSelect){
			KeyValuePair kv = ((EXSelect<KeyValuePair>) container).getValue();
			try{
			Class clazz =  Class.forName(kv.getKey());
			openWorkflow(clazz);
			}catch(Exception e){
				throw new UIException(e);
			}
			
		}else{
			State state = createNewState();
			request.put("created", "true");
		}
		
		
		return true;
	}
	
	
	public EXState getState(State state){
		for(Container c : statesContainer.getChildren()){
			
			EXState ui = (EXState)c;
			State st = ui.getState();
			if(st.getId() == state.getId()){
				return ui;
			}
		}
		
		return null;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("created")){
			return ;
		}
		
		if(request.containsKey("clicksource")){
			return;
		}
		
		if(request.containsKey("source")){
			return;
		}
		
		
		JMap connectOpt = new JMap();
		connectOpt.put("source", new Var("info.sourceId")).put("target",new Var("info.targetId"));
		String bind = "instance.bind(\"connection\"," + new Function(container.clone().makeServerRequest(connectOpt, this), new Var("info")).getJavascript() + ")";
		container.eval(bind);
		
		JMap clickconnectOpt = new JMap();
		clickconnectOpt.put("clicksource", new Var("c.sourceId"));
		clickconnectOpt.put("clicktarget", new Var("c.targetId"));
		clickconnectOpt.put("label", new Var("mylabel"));
		
		JQuery clickevent = container.clone().eval("var mylabel=prompt('Label to display for action');c.getOverlay(\"label\").setLabel(mylabel)");
		clickevent.makeServerRequest(clickconnectOpt,this);
		String bindClick = "instance.bind(\"click\"," + new Function(clickevent, new Var("c")).getJavascript() + ")";
		container.eval(bindClick);
		
		for(Container c : statesContainer.getChildren()){
			
			EXState source = (EXState)c;
			State state = source.getState();
			List<org.castafiore.erp.Process> processes = state.getActions();
			for(Process p :  processes){
				State dest = p.getTarget();
				String label = p.getLabel();
				EXState target = getState(dest);
				if(target != null){
					container.eval("instance.connect({ source:\""+source.getId()+"\", target:\""+target.getId()+"\" }).getOverlay('label').setLabel('"+label+"');");
				}
			}
		}
		
	}

	@Override
	public void addPopup(Container popup) {
		overlay.addChild(popup);
		
	}
	
	
	

}
