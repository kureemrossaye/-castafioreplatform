package org.castafiore.erp.ui.form.controls.search;

import java.math.BigInteger;
import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.annotations.Field;
import org.castafiore.erp.ui.form.controls.GridFrame;
import org.castafiore.erp.ui.form.controls.MagicSelectControl;
import org.castafiore.erp.ui.form.controls.MultiSelectControl;
import org.castafiore.erp.ui.form.controls.MultiSelectControl.OnChangeListener;
import org.castafiore.erp.ui.grid.GridColumn;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class LookupSearchControl extends EXContainer implements  OnChangeListener, Event{

	
	private MultiSelectControl control_;
	
	private GridFrame frame_;
	
	public LookupSearchControl( String field,Field prop, Class<? extends BaseErpModel> vo,  GridFrame frame)throws Exception {
		super(field,"div");
		int[] status =prop.status();
		BigInteger[] bss = new BigInteger[status.length];
		int count = 0;
		for(int i : status){
			
			bss[i] = new BigInteger(status[count] + "");
			count++;
		}
		//<a id="10734238" name="remove" class="close">x</a>
	
		control_ = new MultiSelectControl("control", (Class)prop.lookupModel(), prop.dataLocator().newInstance(), bss);
		control_.addOnChangeListener(this);
		control_.getDescendentOfType(MagicSelectControl.class).setStyle("min-width", "250px");
		addChild(control_.setStyle("min-width", "250px"));
		frame_ = frame;
		control_.setStyle("color", "black");
		
		Container ok = new EXContainer("ok", "a").setAttribute("href", "#").setAttribute("title", "Apply filter").setText("<img src='style/icons/tick-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(ok);
		
		Container close = new EXContainer("remove", "a").setAttribute("href", "#").setAttribute("title", "Close").setText("<img src='style/icons/cross-button.png'></img>").setStyle("float", "right").setStyle("margin", "3px").addEvent(this, CLICK);
		addChild(close);
		
	}

	
	


	@Override
	public void onChange(MultiSelectControl control) {
		frame_.search(control_.getValue(),getName());
		
	}





	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}





	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		getAncestorOfType(GridColumn.class).hide();
		
		return true;
	}





	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	
}
