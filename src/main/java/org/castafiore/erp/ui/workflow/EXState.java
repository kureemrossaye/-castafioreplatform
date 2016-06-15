package org.castafiore.erp.ui.workflow;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.castafiore.erp.State;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.Function;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EXState extends EXContainer implements Event{

	
	private final static ObjectMapper reader = new ObjectMapper(); 
	
	public final static Random RAND = new Random();
	
	private State state = null;
	
	private Container label = new EXContainer("label", "a");
	
	private EXEditState edit = null;
	
	
	public EXState(State state) {
		super(state.getLabel(), "div");
		this.state = state;
		addClass("w");
		
		
		Container sourceKnob = new EXContainer("sourceKnob", "div").addClass("ep");
		label.setText(state.getLabel());
		
		addChild(label.addEvent(this, DOUBLE_CLICK));
		addChild(sourceKnob);
		edit = new EXEditState("edit", this);
		addChild(edit.setDisplay(false));
		
		setStyle("background-color", state.getColor());
		
		BigInteger top = state.getPositionTop();
		BigInteger left = state.getPositionLeft();
		boolean save = false;
		if(top == null){
			top = new BigInteger(RAND.nextInt(300) +"");
			save=true;
		}
		
		if(left == null){
			left = new BigInteger(RAND.nextInt(300) +"");
			save=true;
		}
		
		if(save){
			state.setPositionTop(top);
			state.setPositionLeft(left);
			SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(state);
		}
		
		
		
		setStyle("top", top.toString()+ "px").setStyle("left",  left.toString() + "px");
		addEvent(this, MISC);
	}
	
	public void save(){
		SpringUtil.getBeanOfType(Dao.class).getSession().load(state, state.getId());
		SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(state);
	}
	@Override
	public void ClientAction(JQuery container) {
		
		container.server(this);
	}
	
	public void copy(){
		
		
	}
	
	
	
	public void delete(){
		this.remove();
	}
	
	
	public void edit(){
		label.setDisplay(false);
		edit.setDisplay(true);
		setStyle("z-index", "5");
	}
	
	public void savePosition(BigInteger top, BigInteger left){
		SpringUtil.getBeanOfType(Dao.class).getSession().load(state, state.getId());
		
		state.setPositionLeft(left);
		state.setPositionTop(top);
		
		SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(state);
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(request.containsKey("ctxclicked")){
			
			
			String clicked = request.get("ctxclicked");
			if(clicked.equals("edit")){
				edit();
			}else if(clicked.equals("delete")){
				delete();
			}else if(clicked.equals("copy")){
				copy();
			}
			
			
			return true;
		}
		
		if(container instanceof  EXState){
			try{
				HashMap map = reader.readValue(request.get("ui"), HashMap.class);
				BigInteger top = new BigInteger(new Double(((Map)map.get("position")).get("top").toString()).intValue() + "");
				BigInteger left = new BigInteger( new Double( ((Map)map.get("position")).get("left").toString()).intValue() + "");
				savePosition(top,left);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return true;
		}
		
		if(container.getName().equalsIgnoreCase("label")){
			edit();
		}
		return true;
	}
	
	public void setLabel(String label){
		this.label.setText(label + ":" + state.getValue().intValue()).setDisplay(true);
	}
	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		//container.eval("$('#"+labelInput.getId()+"').focus()");
		
	}
	
	
	private final static String[] contextMenu = new String[]{"Edit","Copy", "Delete"};
	
	public void onReady(JQuery container){
		String id = getId();
		String makesource ="instance.makeSource($('#"+id+"'), makeSourceOptions);";
		makesource = makesource + "instance.makeTarget($('#"+id+"'), makeTargetOptions);";
		JMap opt = new JMap().put("ui", new Var("JSON.stringify(ui)"));
		JMap draggableopt = new JMap().put("stop",new Function(container.clone().makeServerRequest(opt,this), new Var("event"), new Var("ui")));
		
		makesource = makesource + "instance.draggable($('#"+id+"'),"+draggableopt.getJavascript()+" )";
		container.eval(makesource);
		
		
		
		
		//create context menu
		JMap ctxMenuOpt = new JMap();
		JMap menu = new JMap();
		
		for(String item : contextMenu){
			JMap jItem = new JMap();
			jItem.put("name", item).put("icon", item.toLowerCase());
			menu.put(item.toLowerCase(), jItem);
		}
		
		ctxMenuOpt.put("items", menu);
		ctxMenuOpt.put("selector", "a");
		Function callback = new Function(container.clone().makeServerRequest(new JMap().put("ctxclicked", new Var("key")), this), new Var("key"), new Var("options"));
		ctxMenuOpt.put("callback",callback);
		
		container.getCSS("http://medialize.github.io/jQuery-contextMenu/src/jquery.contextMenu.css");
		container.getScript("http://medialize.github.io/jQuery-contextMenu/src/jquery.contextMenu.js", container.clone().invoke("contextMenu", ctxMenuOpt));
		
		
	}
	
	public State getState(){
		return state;
	}

}
