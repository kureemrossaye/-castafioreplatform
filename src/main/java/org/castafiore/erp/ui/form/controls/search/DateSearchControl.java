package org.castafiore.erp.ui.form.controls.search;

import java.util.Date;
import java.util.Map;

import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.ValidationException;

public class DateSearchControl extends EXContainer implements Event{
	
	private EXDatePicker from;
	
	private EXDatePicker to;
	
	private GridFrame frame;
	

	public DateSearchControl(String field, GridFrame frame) {
		super(field, "div");
		this.frame = frame;
	
		from = new EXDatePicker("from");
		to = new EXDatePicker("to");
		from.requestFocus();
		addChild(new EXContainer("", "span").setText("From :")).addChild(from).addChild(new EXContainer("", "span").setText("To :")).addChild(to);
		
		//Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setAttribute("title", "Apply filter").setText("<img src='style/icons/tick-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setAttribute("title", "Apply filter").setText("Apply").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(ok);
		
		//Container close = new EXContainer("remove", "a").setAttribute("href", "#").setAttribute("title", "Close").setText("<img src='style/icons/cross-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		Container close = new EXContainer("remove", "a").setAttribute("href", "#").setAttribute("title", "Close").setText("Cancel").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(close);
	}




	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(container.getName().equals("remove")){
			getAncestorOfType(GridColumn.class).hide();
			return true;
		}
		try{
			Date dfrom = from.getValue();
			Date dto = to.getValue();
			
			frame.search(dfrom, dto, getName());
			getAncestorOfType(GridColumn.class).hide();
		}catch(ValidationException ve){
			
		}
		
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		
	}

}
