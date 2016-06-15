package org.castafiore.erp.ui.form;

import java.util.Map;

import org.castafiore.erp.ui.form.controls.InputControl;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.JQuery;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXCheckBox;

public class ApplyChargeDecorator implements FieldDecorator, Event{

	private IGroup form_;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private void setEnabled(boolean enabled){
		form_.getField("rate").setEnabled(enabled);
		form_.getField("when").setEnabled(enabled);
		form_.getField("rateAppliedOn").setEnabled(enabled);
		form_.getField("applyCharges").setEnabled(enabled);
		
		
	}

	@Override
	public void decorateField(InputControl<?> field, IGroup form) {
		this.form_ = form;
		field.addEvent(this	, CHANGE);
		
	}

	@Override
	public void ClientAction(JQuery container) {
		container.server(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		setEnabled(container.getAncestorOfType(EXCheckBox.class).isChecked());
		
		return true;
	}

	@Override
	public void Success(JQuery container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
