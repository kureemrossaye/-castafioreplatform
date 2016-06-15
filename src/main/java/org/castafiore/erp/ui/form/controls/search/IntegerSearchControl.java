package org.castafiore.erp.ui.form.controls.search;

import java.math.BigInteger;
import java.util.Map;

import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.form.controls.IntegerControl;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class IntegerSearchControl extends EXContainer implements Event{
	
	private IntegerControl from;
	
	private IntegerControl to;
	
	private GridFrame frame;
	

	public IntegerSearchControl(String field, GridFrame frame) {
		super(field, "div");
		this.frame = frame;
		from = new IntegerControl("from");
		to = new IntegerControl("to");
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
		
		BigInteger dfrom = from.getValue();
		BigInteger dto = to.getValue();
		
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
