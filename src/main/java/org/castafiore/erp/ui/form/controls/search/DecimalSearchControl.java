package org.castafiore.erp.ui.form.controls.search;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.castafiore.erp.ui.form.controls.DecimalControl;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.form.controls.IntegerControl;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class DecimalSearchControl extends EXContainer implements Event{
	
	private DecimalControl from;
	
	private DecimalControl to;
	
	private GridFrame frame;
	

	public DecimalSearchControl(String field, GridFrame frame) {
		super(field, "div");
		this.frame = frame;
		
		from = new DecimalControl("from");
		to = new DecimalControl("to");
		from.requestFocus();
		addChild(new EXContainer("", "span").setText("From :")).addChild(from).addChild(new EXContainer("", "span").setText("To :")).addChild(to);
		Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setAttribute("title", "Apply filter").setText("<img src='style/icons/tick-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(ok);
		
		Container close = new EXContainer("remove", "a").setAttribute("href", "#").setAttribute("title", "Close").setText("<img src='style/icons/cross-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(close);
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		BigDecimal dfrom = from.getValue();
		BigDecimal dto = to.getValue();
		
		frame.search(dfrom, dto, getName());
		getAncestorOfType(GridColumn.class).hide();
		return true;
	}


	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
