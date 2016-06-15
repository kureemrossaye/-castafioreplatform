package org.castafiore.erp.ui.sales;

import java.util.Map;

import org.castafiore.erp.BaseErpModel;
import org.castafiore.erp.SalesOrder;
import org.castafiore.erp.ui.form.Forms;
import org.castafiore.erp.ui.form.Group;
import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.erp.ui.grid.GridController;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXButton;

public class ShippingInformation extends Group implements Event{
	
	private EXButton sameAsBilling = new EXButton("sameAs", "Same as billing information");

	public ShippingInformation(org.castafiore.erp.annotations.Group group,
			Class<? extends BaseErpModel> vo_, GridController controller) {
		super(group, vo_, controller);
		addChildAt(sameAsBilling, 1);
		sameAsBilling.addClass("btn").addClass("btn-xs").addEvent(this, CLICK);
		
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		SalesOrder tmp  = new SalesOrder();
		
		getAncestorOfType(Forms.class).getField("billingAddress").fillVo(tmp);
		
		//fillModel(model)
		InputControl field = getField("shippingAddress");
		field.setValue(tmp.getBillingAddress());
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
