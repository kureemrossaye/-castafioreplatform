package org.castafiore.erp.ui.form.controls;
import static org.castafiore.erp.utils.ReflectionUtils.getAnnotation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.bean.BeanUtil;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.ConfigModel;
import org.castafiore.erp.annotations.Column;
import org.castafiore.erp.annotations.DropDown;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.annotations.Table;
import org.castafiore.erp.ui.configs.UIConfig;
import org.castafiore.erp.ui.grid.GridDataLocator;
import org.castafiore.erp.ui.grid.QueryParams;
import org.castafiore.erp.utils.ReflectionUtils;
import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.ui.mvc.CastafioreController;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.utils.StringUtil;
import org.springframework.web.servlet.ModelAndView;

public class MagicSelectControl<T extends BaseErpModel> extends EXContainer implements InputControl<T>, CastafioreController, Event{
	
	private Class<? extends BaseErpModel> vo_;
	

	private T selectedItem = null;
	private Table tableDefn;
	
	private List<OnSelectListener<T>> listeners = new ArrayList<MagicSelectControl.OnSelectListener<T>>(1);
	
	
	
	private List<? extends BaseErpModel> suggestions = new ArrayList<BaseErpModel>();
	
	private JMap options = new JMap();
	
	
	private BigInteger[] status = null;
	
	private GridDataLocator locator;

	public MagicSelectControl(String name,final  Class<? extends BaseErpModel> vo, GridDataLocator locator, BigInteger[] status) {
		super(name, "input");
		this.vo_ = vo;
		setReadOnlyAttribute("type", "text");
		addClass("form-control");
		this.locator =locator;
		tableDefn = getAnnotation(vo_,Table.class);
		addEvent(this, MISC);
		if(ConfigModel.class.isAssignableFrom(vo)){
			setAttribute("title", "Double click to create new");
		addEvent(new Event() {
			
			@Override
			public void Success(JQuery container, Map<String, String> request)
					throws UIException {
				
			}
			
			@Override
			public boolean ServerAction(Container container, Map<String, String> request)
					throws UIException {
				
				Class clasz = vo;
				UIConfig config = new UIConfig(clasz);
				container.getAncestorOfType(PopupContainer.class).addPopup(config);
				return true;
			}
			
			@Override
			public void ClientAction(JQuery container) {
				container.server(this);
				
			}
		}, Event.DOUBLE_CLICK);
		}
	}
	
	
	public Field getField(String name){
		return ReflectionUtils.findField(vo_, name).getAnnotation(Field.class);
	}

	@Override
	public T getValue() {
		return selectedItem;
	}

	@Override
	public void setValue(T value) {
		this.selectedItem = value;
		if(value != null)
			setAttribute("value", value.toString());
		else
			setAttribute("value", "");
		//setRendered(false);
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		options.put("enabled", enabled);
		setRendered(false);
	}

	@Override
	public String getProperty() {
		return getName();
	}

	@Override
	public void fillVo(BaseErpModel model) {
		BeanUtil.setProperty(model, getProperty(), getValue());
		
	}

	@Override
	public void fillControl(BaseErpModel model) {
		
		T value = (T)BeanUtil.getProperty(model, getProperty());
		setValue(value);
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sortBy = request.getParameter("sidx");
		String sortDir = request.getParameter("sord");
		String term = request.getParameter("searchTerm");
		
		int iPage = 0;
		if(StringUtil.isNotEmpty(page)){
			iPage = Integer.parseInt(page)-1;
		}
		
		int iRows = 10;
		if(StringUtil.isNotEmpty(rows)){
			iRows = Integer.parseInt(rows);
		}
		
		
		
		//SearchEngine engine = SpringUtil.getBeanOfType(SearchEngine.class);
		//suggestions = engine.search(term, vo_, iPage, iRows, status);
		QueryParams params = new QueryParams();
		params.setPage(iPage);
		params.setPagSize(iRows);
		params.getOrders().add(new SimpleKeyValuePair(sortBy,sortDir));
		params.setFullTextSearchTerm(term);
		suggestions = locator.loadData(vo_, params).getData();
		JMap result = new JMap();
		JArray records = new JArray();
		String[] columns  = null;
		
		if(getAnnotation(vo_,DropDown.class) != null)
			columns = getAnnotation(vo_,DropDown.class).columns();
		else if(getAnnotation(vo_,Table.class) != null){
			columns = getAnnotation(vo_,Table.class).columns();
		}else{
			throw new RuntimeException("either Table or DropDown annotations should be present on Entity "  + vo_.getSimpleName() );
		}
		for(BaseErpModel model : suggestions){
			JMap record = new JMap();
			record.put("id",model.getId());
			
			for(String field : columns){
				Object val = BeanUtil.getProperty(model, field);
				if(val == null){
					val = "";
				}else{
					val = val.toString();
				}
				record.put(field, val.toString());
			}
			records.add(record);
			
		}
		result.put("page", 1);
		result.put("records", suggestions.size());
		result.put("total", 1);
		result.put("rows", records);
		response.getOutputStream().write(result.getJavascript().getBytes());
		
		
		
		return null;
	}
	
	
	
	public void onReady(JQuery jquery){
		JMap opt = new JMap();
		opt.put("url", ResourceUtil.getMethodUrl(this));
		JArray colModel = new JArray();
		JMap id = new JMap().put("columnName", "id").put("label","#").put("width",5).put("hidden", true);
		colModel.add(id);
		String[] columns  = null;
		if(ReflectionUtils.getAnnotation(vo_,DropDown.class) != null)
			columns = getAnnotation(vo_,DropDown.class).columns();
		else if(ReflectionUtils.getAnnotation(vo_,Table.class) != null){
			columns = getAnnotation(vo_,Table.class).columns();
		}else{
			throw new RuntimeException("either Table or DropDown annotations should be present on Entity "  + vo_.getSimpleName() );
		}
		
		for(String field : columns){
			JMap col = new JMap();
			System.out.println(vo_.getSimpleName() + "." + field);
			Column ann = ReflectionUtils.findField(vo_, field).getAnnotation(Column.class);
			String align = "left";
			
			Class type = ReflectionUtils.findField(vo_, field).getType();
			if(type.equals(BigInteger.class) || type.equals(BigDecimal.class)){
				align ="right";
			}
			
			col.put("columnName", field).put("label",ann.caption()).put("width",(100)/columns.length).put("align", align);
			colModel.add(col);
		}
		
		opt.put("colModel",colModel);
		opt.put("alternate",true).put("autoChoose",false).put("showOn",true).put("autoFocus",false);
		opt.put("select", jquery.clone().makeServerRequest(new JMap().put("selected",new Var("ui.item.id")), this),"event", "ui");
		jquery.invoke("combogrid", opt);
		//jquery.invoke("bt", new JMap().put("trigger",  new JArray().add("F1").add("blur")).put("position", new JArray().add("right")));
	}
	
		
	public void addOnSelectListener(OnSelectListener<T> listener){
		listeners.add(listener);
	}


	@Override
	public void ClientAction(JQuery container) {
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		try{
		String selected = request.get("selected");
		 //Integer[] iselec = mapper.readValue(selected, Integer[].class);
		 //Integer primaryKey = iselec[0];
		 
		 this.selectedItem =  (T)locator.findById(vo_, Integer.parseInt(selected)) ;//(T)SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().get(vo_, Integer.parseInt(selected));
		 setValue(this.selectedItem);
		 for(OnSelectListener<T> listener : listeners){
			 listener.onSelect(this, selectedItem);
		 }
		 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		container.focus(container.clone());
	}
	
	public interface OnSelectListener<T>{
		public void onSelect(MagicSelectControl<? extends BaseErpModel> source, T selected);
	}
	

}
