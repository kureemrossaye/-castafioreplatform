package org.castafiore.erp.ui.form.controls.search;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.search.SearchEngine;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.AutoCompleteSource;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.js.JArray;

public class TextSearchControl extends EXContainer implements AutoCompleteSource, Event{

	private Class<? extends BaseErpModel> vo_;
	
	
	private String field_;
	
	private EXAutoComplete autocomplete;
	
	private GridFrame frame;
	
	public TextSearchControl( String field,Field prop, Class<? extends BaseErpModel> vo,  GridFrame frame) {
		super(field, "div");
		this.vo_=vo;
		this.field_ = field;
		this.frame = frame;
		
		autocomplete = new EXAutoComplete("autp", "");
		autocomplete.setSource(this);
		
		autocomplete.addEvent(this, CHANGE);
		addChild(autocomplete.setStyle("min-width", "250px"));
		
		Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setAttribute("title", "Apply filter").setText("<img src='style/icons/tick-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(ok);
		
		Container close = new EXContainer("remove", "a").setAttribute("href", "#").setAttribute("title", "Close").setText("<img src='style/icons/cross-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(close);
		
	}
	
	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("remove")){
			getAncestorOfType(GridColumn.class).hide();
			return true;
		}
		
		frame.search(autocomplete.getValue(),getName());
		getAncestorOfType(GridColumn.class).hide();
		
		return true;
	}
	
	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public JArray getSource(String param) {
		JArray res = new JArray();
		for(String s : SpringUtil.getBeanOfType(SearchEngine.class).suggestField(param, vo_, field_ )){
			res.add(s);
		}
		
		return res;
	}

	
}
